package com.example.food.controller;

import com.example.food.entity.DTOs.FoodDTOSearch;
import com.example.food.entity.Food;
import com.example.food.repository.FoodRepository;
import com.example.food.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class FoodController {

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    FoodService foodService;
    @GetMapping
    public List<Food> getAllFoods() {
        return foodRepository.findAll();
    }

    @GetMapping("/product")
    public float getFoodPrice(@RequestParam long foodId) {
        Optional<Food> food = foodRepository.findById(foodId);
        if(food.isPresent()) {
            return food.get().getPrice();
        }
        throw new IllegalArgumentException();
    }

    @GetMapping("/product-name")
    public String getFoodPrice(@RequestParam Long foodId) {
        Optional<Food> food = foodRepository.findById(foodId);
        if(food.isPresent()) {
            return food.get().getName();
        }
        throw new IllegalArgumentException();
    }

    @GetMapping("/product-desc")
    public String getFoodDesc(@RequestParam Long foodId) {
        Optional<Food> food = foodRepository.findById(foodId);
        if(food.isPresent()) {
            return food.get().getDescription();
        }
        throw new IllegalArgumentException();
    }

    @GetMapping("/search")
    public ResponseEntity<List<FoodDTOSearch>> searchFood(@RequestParam String keyword) {
        return foodService.searchFood(keyword);
    }
}
