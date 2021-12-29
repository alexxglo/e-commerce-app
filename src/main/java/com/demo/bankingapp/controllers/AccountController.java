package com.demo.bankingapp.controllers;

import com.demo.bankingapp.entities.Account;
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
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    TransactionService transactionService;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @PostMapping
    public ResponseEntity<Account> addAccount(@RequestParam Long openerAccountId) {
        try {
            return new ResponseEntity<>(accountService.addAccount(openerAccountId), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/balance/{accountId}")
    public ResponseEntity<Account> addBalance(@RequestParam float amount, @PathVariable Long accountId) {
        try {
            return new ResponseEntity<>(accountService.addBalance(amount, accountId), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
