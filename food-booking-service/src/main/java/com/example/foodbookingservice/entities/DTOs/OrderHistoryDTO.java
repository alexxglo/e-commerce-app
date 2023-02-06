package com.example.foodbookingservice.entities.DTOs;

import java.util.List;

public class OrderHistoryDTO {

    private Long orderId;

    private List<FoodDTOSearch> foodList;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<FoodDTOSearch> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<FoodDTOSearch> foodList) {
        this.foodList = foodList;
    }
}
