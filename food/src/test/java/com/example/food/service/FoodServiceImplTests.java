package com.example.food.service;

import com.example.food.entity.Food;
import com.example.food.repository.FoodRepository;
import com.example.food.service.impl.FoodServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class FoodServiceImplTests {

    @InjectMocks
    FoodServiceImpl foodServiceImpl;

    @Mock
    FoodRepository foodRepository;

    @Test
    public void testSearchFood() {
        String keyword = "keyword";

        List<Food> foodList = new ArrayList<>();
        Food food = new Food();
        foodList.add(food);
        Mockito.when(foodRepository.getProductByNameContainingKeyword(keyword)).thenReturn(foodList);

        assertEquals(foodServiceImpl.searchFood(keyword).getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testSearchFoodBadFlow() {
        String keyword = "keyword";

        List<Food> foodList = new ArrayList<>();

        Mockito.when(foodRepository.getProductByNameContainingKeyword(keyword)).thenReturn(foodList);

        assertEquals(foodServiceImpl.searchFood(keyword).getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
