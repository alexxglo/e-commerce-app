package com.example.ecommerceservice.feignclient;

import com.example.ecommerceservice.entities.DTOs.FundResponse;
import com.example.ecommerceservice.entities.DTOs.ProductDTO;
import com.example.ecommerceservice.entities.DTOs.TransactionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "http://FUND-SERVICE/fund")
//@FeignClient(value = "fund-service", url = "http://localhost:8082/fund/accounts")
public interface FundClient {

    @GetMapping("/accounts/buy/product")
    ResponseEntity<FundResponse> buyProduct(@RequestParam Long accountNumber, @RequestParam float price);

    @PostMapping("/transactions/history")
    ResponseEntity<TransactionResponse> addProductToHistory(@RequestParam Long accountNumber, @RequestBody ProductDTO product);

    @GetMapping("transactions/history/{accountNumber}")
    List<TransactionResponse> getHistoryOfAccount(@PathVariable Long accountNumber);
}
