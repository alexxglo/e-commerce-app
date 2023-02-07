package com.example.foodbookingservice.controllers;

import com.example.foodbookingservice.entities.DTOs.FoodDTO;
import com.example.foodbookingservice.entities.DTOs.FoodDTOSearch;
import com.example.foodbookingservice.entities.DTOs.OrderDTO;
import com.example.foodbookingservice.entities.DTOs.OrderHistoryDTO;
import com.example.foodbookingservice.services.FoodBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/food-booking")
public class FoodBookingController {

    @Autowired
    FoodBookingService foodBookingService;

    @PostMapping("/order")
    ResponseEntity<OrderDTO> placeOrder (@RequestParam Long userId, @RequestBody List<FoodDTO> order) {
        return foodBookingService.placeOrder(userId, order);
    }

    @GetMapping("/search")
    ResponseEntity<List<FoodDTOSearch>> searchFood (@RequestParam String keyword) {
        return foodBookingService.searchFood(keyword);
    }

    @GetMapping("/order-history")
    ResponseEntity<List<OrderHistoryDTO>> getOrderHistoryForUser (@RequestParam Long userId) {
        return foodBookingService.getOrderHistoryForUser(userId);
    }
}
