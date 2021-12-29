package com.demo.bankingapp.controllers;

import com.demo.bankingapp.entities.DTOs.TransactionDTO;
import com.demo.bankingapp.entities.Transaction;
import com.demo.bankingapp.services.AccountService;
import com.demo.bankingapp.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    AccountService accountService;

    @PostMapping("/transfer")
    public ResponseEntity<String> transferFunds(@RequestBody TransactionDTO transaction) {
        try {
            accountService.transferFunds(transaction);
            transactionService.markTransaction(transaction);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/transfer/history")
    public List<Transaction> getTransactionsHistoryByYearAndMonth(@RequestParam String date, @RequestParam Long accountId) {
        return transactionService.getTransactionsHistoryByYearAndMonth(date, accountId);
    }
}
