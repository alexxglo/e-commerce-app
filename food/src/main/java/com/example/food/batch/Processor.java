package com.example.food.batch;

import com.example.food.entity.Food;
import com.example.food.repository.FoodRepository;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Processor implements ItemProcessor<Food, Food> {
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Food process(Food food) throws Exception {
        return food;
    }
}
