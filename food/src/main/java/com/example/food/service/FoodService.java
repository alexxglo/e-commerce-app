package com.example.food.service;

import com.example.food.entity.DTOs.FoodDTOSearch;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FoodService {

    ResponseEntity<List<FoodDTOSearch>> searchFood(String keyword);
}
