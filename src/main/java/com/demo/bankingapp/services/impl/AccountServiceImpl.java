package com.demo.bankingapp.services.impl;

import com.demo.bankingapp.entities.Account;
import com.demo.bankingapp.entities.DTOs.TransactionDTO;
import com.demo.bankingapp.entities.Transaction;
import com.demo.bankingapp.repositories.AccountRepository;
import com.demo.bankingapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account addBalance(float amount, Long accountId) {
        if(amount <= 0) {
            throw new IllegalArgumentException();
        }
        Account account = accountRepository.getById(accountId);
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    @Override
    public Account transferFunds(TransactionDTO transaction) {
        try {
            addBalance(transaction.getAmount(), transaction.getReceiverId());
            lowerBalance(transaction.getAmount(), transaction.getSenderId());
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
        return accountRepository.findById(transaction.getSenderId()).get();
    }

    public Account lowerBalance(float amount, Long accountId) {
        Account account = accountRepository.getById(accountId);
        account.setBalance(account.getBalance() - amount);
        if(account.getBalance() < 0) {
            throw new IllegalArgumentException();
        }
        return accountRepository.save(account);
    }
}
