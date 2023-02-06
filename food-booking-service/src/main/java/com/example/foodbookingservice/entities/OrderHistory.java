package com.example.foodbookingservice.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private float totalPrice;

    @OneToMany(mappedBy = "orderHistory")
    private List<OrderDetails> foodList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderDetails> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<OrderDetails> foodList) {
        this.foodList = foodList;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
