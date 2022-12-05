package com.example.ecommerceservice.controllers;

import com.example.ecommerceservice.entities.DTOs.FundResponse;
import com.example.ecommerceservice.entities.DTOs.TransactionResponse;
import com.example.ecommerceservice.entities.Product;
import com.example.ecommerceservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    ProductService productService;

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam String keyword) {
        return productService.searchProduct(keyword);
    }

    @GetMapping("/buy")
    public ResponseEntity<FundResponse> buyProduct(@RequestParam Long accountNumber, @RequestParam Long productId) {
        return productService.buyProduct(accountNumber, productId);
    }

    @GetMapping("/history/{accountNumber}")
    public List<TransactionResponse> getHistoryOfAccount(@PathVariable Long accountNumber) {
        return productService.getHistoryOfAccount(accountNumber);
    }
}
