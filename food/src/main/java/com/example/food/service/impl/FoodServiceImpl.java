package com.example.food.service.impl;

import com.example.food.entity.DTOs.FoodDTOSearch;
import com.example.food.entity.Food;
import com.example.food.repository.FoodRepository;
import com.example.food.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    FoodRepository foodRepository;

    @Override
    public ResponseEntity<List<FoodDTOSearch>> searchFood(String keyword) {
        List<Food> foodList = foodRepository.getProductByNameContainingKeyword(keyword);
        if(foodList.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(foodListToFoodDTO(foodList), HttpStatus.OK);
    }

    private List<FoodDTOSearch> foodListToFoodDTO(List<Food> foodList) {
        List<FoodDTOSearch> foodDTOSearchList = new ArrayList<>();
        for (Food food : foodList) {
            FoodDTOSearch foodDTOSearch = new FoodDTOSearch();
            foodDTOSearch.setDescription(food.getDescription());
            foodDTOSearch.setName(food.getName());
            foodDTOSearch.setPrice(food.getPrice());
            foodDTOSearchList.add(foodDTOSearch);
        }
        return foodDTOSearchList;
    }
}
