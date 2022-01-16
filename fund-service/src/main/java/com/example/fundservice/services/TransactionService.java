package com.example.fundservice.services;

import com.example.fundservice.entities.DTOs.ProductResponse;
import com.example.fundservice.entities.DTOs.TransactionDTO;
import com.example.fundservice.entities.Transaction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TransactionService {

    Transaction markTransaction(TransactionDTO transaction);

    List<Transaction> getTransactionsHistoryByYearAndMonth(String date, Long accountId);

    ResponseEntity<TransactionDTO> addProductToHistory(Long accountNumber, ProductResponse productResponse);

    List<TransactionDTO> getHistoryOfAccount(Long accountNumber);
}
