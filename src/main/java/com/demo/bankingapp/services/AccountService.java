package com.demo.bankingapp.services;

import com.demo.bankingapp.entities.Account;
import com.demo.bankingapp.entities.DTOs.TransactionDTO;
import com.demo.bankingapp.entities.Transaction;

import java.util.List;

public interface AccountService {

    public List<Account> getAllAccounts();

    public Account addBalance(float amount, Long accountId);

    public Account transferFunds(TransactionDTO transaction);
}
