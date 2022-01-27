package com.example.foodbookingservice.services.impl;

import com.example.foodbookingservice.entities.DTOs.*;
import com.example.foodbookingservice.entities.OrderDetails;
import com.example.foodbookingservice.entities.OrderHistory;
import com.example.foodbookingservice.feignclient.FoodClient;
import com.example.foodbookingservice.feignclient.FundClient;
import com.example.foodbookingservice.repositories.OrderDetailsRepository;
import com.example.foodbookingservice.repositories.OrderHistoryRepository;
import com.example.foodbookingservice.services.FoodBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodBookingServiceImpl implements FoodBookingService {

    @Autowired
    OrderHistoryRepository orderHistoryRepository;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    @Autowired
    FoodClient foodClient;

    @Autowired
    FundClient fundClient;

    @Override
    public ResponseEntity<OrderDTO> placeOrder(Long userId, List<FoodDTO> order) {
        try {
            fundClient.searchUser(userId);
            OrderHistory orderHistory = makeNewOrder(userId, getTotalPriceOfOrder(order), order);
            List<OrderDetails> orderDetails = foodDTOtoOrderDetails(order);
            orderHistory.setFoodList(orderDetails);
            OrderHistory orderHistorySaved = orderHistoryRepository.save(orderHistory);

            saveFood(orderDetails, orderHistorySaved);
            ProductDTO productResponse = setProductResponse(orderHistorySaved);

            fundClient.buyProduct(userId, orderHistorySaved.getTotalPrice());
            fundClient.addProductToHistory(userId, productResponse);

            return new ResponseEntity<>(makeOrderResponse(orderHistory), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<FoodDTOSearch>> searchFood(String keyword) {
        return foodClient.searchFood(keyword);
    }

    @Override
    public ResponseEntity<List<OrderHistoryDTO>> getOrderHistoryForUser(Long userId) {
        try {
            List<OrderHistoryDTO> orderList = new ArrayList<>();
            List<OrderHistory> orderHistoryList = orderHistoryRepository.getAllByUserId(userId);

            for(OrderHistory orderHistory : orderHistoryList) {
                List<FoodDTOSearch> dtoFoodList = new ArrayList<>();
                for(OrderDetails product : orderHistory.getFoodList()) {
                    String foodName = foodClient.getFoodName(product.getFoodId());
                    float foodOrderPrice = foodClient.getFoodPrice(product.getFoodId()) * product.getQuantity();
                    String foodDescription = foodClient.getFoodDesc(product.getFoodId());
                    FoodDTOSearch foodOrder = new FoodDTOSearch();
                    foodOrder.setName(foodName);
                    foodOrder.setPrice(foodOrderPrice);
                    foodOrder.setDescription(foodDescription);
                    dtoFoodList.add(foodOrder);
                }
                    OrderHistoryDTO orderHistoryDTO = new OrderHistoryDTO();
                    orderHistoryDTO.setOrderId(orderHistory.getId());
                    orderHistoryDTO.setFoodList(dtoFoodList);
                    orderList.add(orderHistoryDTO);
                }
            return new ResponseEntity<>(orderList, HttpStatus.OK);
            }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    private List<OrderDetails> foodDTOtoOrderDetails(List<FoodDTO> order) {
        List<OrderDetails> orderList = new ArrayList<>();
        for(FoodDTO foodProduct : order) {
            OrderDetails newProduct = new OrderDetails();
            newProduct.setFoodId(foodProduct.getFoodId());
            newProduct.setQuantity(foodProduct.getQuantity());
            orderList.add(newProduct);
        }
        return orderList;
    }

    private float getTotalPriceOfOrder(List<FoodDTO> order) {
        float totalPrice = 0;
        for (FoodDTO foodProduct : order) {
            totalPrice = totalPrice + foodProduct.getQuantity() * foodClient.getFoodPrice(foodProduct.getFoodId());
        }
        return totalPrice;
    }
    private OrderHistory makeNewOrder(Long userId, float totalPrice, List<FoodDTO> order) {
        OrderHistory newOrder = new OrderHistory();
        newOrder.setUserId(userId);
        newOrder.setTotalPrice(totalPrice);
        return newOrder;
    }
    private OrderDTO makeOrderResponse(OrderHistory orderHistory) {
        OrderDTO orderResponse = new OrderDTO();
        orderResponse.setUserId(orderHistory.getUserId());
        orderResponse.setTotalPrice(orderHistory.getTotalPrice());
        return orderResponse;
    }

    private ProductDTO setProductResponse(OrderHistory orderHistory) {
        ProductDTO productResponse = new ProductDTO();
        productResponse.setPrice((int) orderHistory.getTotalPrice());
        productResponse.setName("Food order");
        return productResponse;
    }

    private void saveFood(List<OrderDetails> orderDetails, OrderHistory orderHistorySaved) {
        for (OrderDetails foodProduct : orderDetails) {
            foodProduct.setOrderHistory(orderHistorySaved);
            orderDetailsRepository.save(foodProduct);
        }
    }
}
