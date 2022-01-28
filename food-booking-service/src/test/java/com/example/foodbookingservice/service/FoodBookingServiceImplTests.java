package com.example.foodbookingservice.service;

import com.example.foodbookingservice.entities.DTOs.*;
import com.example.foodbookingservice.entities.OrderDetails;
import com.example.foodbookingservice.entities.OrderHistory;
import com.example.foodbookingservice.feignclient.FoodClient;
import com.example.foodbookingservice.feignclient.FundClient;
import com.example.foodbookingservice.repositories.OrderDetailsRepository;
import com.example.foodbookingservice.repositories.OrderHistoryRepository;
import com.example.foodbookingservice.services.impl.FoodBookingServiceImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

@ExtendWith(SpringExtension.class)
class FoodBookingServiceImplTests {

    @InjectMocks
    FoodBookingServiceImpl foodBookingServiceImpl;

    @Mock
    OrderHistoryRepository orderHistoryRepository;

    @Mock
    OrderDetailsRepository orderDetailsRepository;

    @Mock
    FundClient fundClient;

    @Mock
    FoodClient foodClient;

    List<FoodDTO> makeOrderList() {
        List<FoodDTO> orderList = new ArrayList<>();
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setFoodId(1l);
        foodDTO.setQuantity(2);
        orderList.add(foodDTO);
        return orderList;
    }

    List<OrderDetails> makeOrderDetailsList() {
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setFoodId(1l);
        orderDetails.setId(1l);
        orderDetails.setQuantity(10);
        orderDetails.setOrderHistory(null);
        orderDetailsList.add(orderDetails);
        return orderDetailsList;
    }

    OrderHistory makeOrderHistory() {
        OrderHistory orderHistory = new OrderHistory();
        orderHistory.setId(1l);
        orderHistory.setUserId(1l);
        orderHistory.setTotalPrice(10);
        orderHistory.setFoodList(makeOrderDetailsList());
        return orderHistory;
    }

    @Test
    void testPlaceOrder() {

        Long userId = 1l;
        List<FoodDTO> orderList = makeOrderList();
        OrderHistory orderHistory = makeOrderHistory();
        OrderHistory orderHistorySaved = makeOrderHistory();
        FundResponse fundResponse = new FundResponse();
        ProductDTO productResponse = new ProductDTO();
        TransactionResponse transactionResponse = new TransactionResponse();
        OrderDTO orderResponse = new OrderDTO();
        orderResponse.setTotalPrice(10);
        orderResponse.setUserId(1l);

        Mockito.when(fundClient.searchUser(userId)).thenReturn(ResponseEntity.ok(userId));
        Mockito.when(orderHistoryRepository.save(any())).thenReturn(orderHistory);
        Mockito.when(foodClient.getFoodPrice(1l)).thenReturn(10f);
        Mockito.when(fundClient.buyProduct(userId, orderHistorySaved.getTotalPrice())).thenReturn(ResponseEntity.ok(fundResponse));
        Mockito.when(fundClient.addProductToHistory(userId, productResponse)).thenReturn(ResponseEntity.ok(transactionResponse));
        ResponseEntity<OrderDTO> actual = foodBookingServiceImpl.placeOrder(userId, orderList);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    void testPlaceOrderBadFlow() {
        Long userId = 1l;
        List<FoodDTO> orderList = makeOrderList();
        ResponseEntity<OrderDTO> actual = foodBookingServiceImpl.placeOrder(userId, orderList);

        Mockito.when(fundClient.searchUser(userId)).thenThrow(IllegalArgumentException.class);

        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    void testSearchFood() {
        String keyword = "keyword";
        List<FoodDTOSearch> list = new ArrayList<>();

        Mockito.when(foodClient.searchFood(keyword)).thenReturn(new ResponseEntity<>(list, HttpStatus.OK));

        assertEquals(ResponseEntity.ok(list), foodBookingServiceImpl.searchFood(keyword));
    }

    @Test
    void testGetOrderHistoryForUser() {
        Long userId = 1l;
        List<OrderHistoryDTO> orderList = new ArrayList<>();
        List<OrderHistory> orderHistoryList = new ArrayList<>();
        OrderHistory orderHistory = makeOrderHistory();
        orderHistoryList.add(orderHistory);

        Mockito.when(orderHistoryRepository.getAllByUserId(userId)).thenReturn(orderHistoryList);
        Mockito.when(foodClient.getFoodName(any())).thenReturn("foodName");
        Mockito.when(foodClient.getFoodDesc(any())).thenReturn("foodDesc");

        assertEquals( HttpStatus.OK, foodBookingServiceImpl.getOrderHistoryForUser(userId).getStatusCode());
    }

    @Test
    void testGetOrderHistoryForUserBadFlow() {
        Long userId = 1l;

        Mockito.when(orderHistoryRepository.getAllByUserId(userId)).thenThrow(IllegalArgumentException.class);

        assertEquals(HttpStatus.NOT_FOUND, foodBookingServiceImpl.getOrderHistoryForUser(userId).getStatusCode());
    }
}
