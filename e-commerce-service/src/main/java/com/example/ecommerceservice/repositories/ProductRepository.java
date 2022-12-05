package com.example.ecommerceservice.repositories;

import com.example.ecommerceservice.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("FROM Product p where p.name LIKE %:keyword%")
    List<Product> getProductByNameContainingKeyword(String keyword);
}
