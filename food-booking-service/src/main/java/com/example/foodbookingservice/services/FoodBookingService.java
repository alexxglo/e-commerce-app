package com.example.foodbookingservice.services;

import com.example.foodbookingservice.entities.DTOs.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FoodBookingService {

    ResponseEntity<OrderDTO> placeOrder(Long userId, List<FoodDTO> order);

    ResponseEntity<List<FoodDTOSearch>> searchFood (String keyword);

    ResponseEntity<List<OrderHistoryDTO>> getOrderHistoryForUser (Long userId);
}
