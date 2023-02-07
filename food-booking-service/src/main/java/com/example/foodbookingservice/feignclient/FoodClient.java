package com.example.foodbookingservice.feignclient;

import com.example.foodbookingservice.entities.DTOs.FoodDTOSearch;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "http://FOOD-SERVICE/food")
public interface FoodClient {

    @GetMapping("/product")
    float getFoodPrice(@RequestParam long foodId);

    @GetMapping("/product-name")
    String getFoodName(@RequestParam Long foodId);

    @GetMapping("/product-desc")
    String getFoodDesc(@RequestParam Long foodId);

    @GetMapping("/search")
    ResponseEntity<List<FoodDTOSearch>> searchFood(@RequestParam String keyword);
}
