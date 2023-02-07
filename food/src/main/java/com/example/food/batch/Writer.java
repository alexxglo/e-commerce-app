package com.example.food.batch;

import com.example.food.entity.Food;
import com.example.food.repository.FoodRepository;
import org.springframework.stereotype.Component;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class Writer implements ItemWriter<Food> {

    @Autowired
    private FoodRepository repo;

    @Override
    @Transactional
    public void write(List<? extends Food> food) throws Exception {
        repo.saveAll(food);
    }
}
