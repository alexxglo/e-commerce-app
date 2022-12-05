package com.example.fundservice.services;

import com.example.fundservice.entities.Account;
import com.example.fundservice.entities.DTOs.AccountDTO;
import com.example.fundservice.entities.DTOs.TransactionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface AccountService {

    public List<Account> getAllAccounts();

    public Account addBalance(float amount, Long accountId);

    public Account transferFunds(TransactionDTO transaction);

    public Account addAccount(Long openerId);

    public ResponseEntity<AccountDTO> buyProduct(Long accountNumber, float price);

    public Long searchUser(Long accountNumber);
}
