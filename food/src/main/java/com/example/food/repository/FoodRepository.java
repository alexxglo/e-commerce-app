package com.example.food.repository;

import com.example.food.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query("FROM Food p where p.name LIKE %:keyword%")
    List<Food> getProductByNameContainingKeyword(String keyword);
}
