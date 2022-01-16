package com.example.ecommerceservice.services;

import com.example.ecommerceservice.entities.DTOs.FundResponse;
import com.example.ecommerceservice.entities.DTOs.TransactionResponse;
import com.example.ecommerceservice.entities.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {

    ResponseEntity<List<Product>> searchProduct(String keyword);

    ResponseEntity<FundResponse> buyProduct(Long accountNumber, Long productId);

    List<TransactionResponse> getHistoryOfAccount(Long accountNumber);
}
