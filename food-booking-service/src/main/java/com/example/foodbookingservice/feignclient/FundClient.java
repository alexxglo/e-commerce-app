package com.example.foodbookingservice.feignclient;

import com.example.foodbookingservice.entities.DTOs.FundResponse;
import com.example.foodbookingservice.entities.DTOs.ProductDTO;
import com.example.foodbookingservice.entities.DTOs.TransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "http://FUND-SERVICE/fund")
public interface FundClient {

    @GetMapping("/accounts/search")
    ResponseEntity<Long> searchUser(@RequestParam Long accountNumber);

    @PostMapping("/transactions/history")
    ResponseEntity<TransactionResponse> addProductToHistory(@RequestParam Long accountNumber, @RequestBody ProductDTO product);

    @GetMapping("/accounts/buy/product")
    ResponseEntity<FundResponse> buyProduct(@RequestParam Long accountNumber, @RequestParam float price);
}
