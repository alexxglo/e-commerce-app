package com.demo.bankingapp.services.impl;

import com.demo.bankingapp.entities.Account;
import com.demo.bankingapp.entities.DTOs.TransactionDTO;
import com.demo.bankingapp.entities.User;
import com.demo.bankingapp.repositories.AccountRepository;
import com.demo.bankingapp.repositories.UserRepository;
import com.demo.bankingapp.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account addBalance(float amount, Long accountId) {
        if (amount <= 0) {
            throw new IllegalStateException("Value below zero");
        }
        Account account = accountRepository.getById(accountId);
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    @Override
    public Account transferFunds(TransactionDTO transaction) {
        try {
            if (accountRepository.findById(transaction.getSenderId()).get() != null
                    && accountRepository.findById(transaction.getReceiverId()).get() != null) {
                addBalance(transaction.getAmount(), transaction.getReceiverId());
                lowerBalance(transaction.getAmount(), transaction.getSenderId());
            } else {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Balance below zero");
        }
        return accountRepository.findById(transaction.getSenderId()).get();
    }

    @Override
    public Account addAccount(Long openerId) {
        if (userRepository.findById(openerId).get() != null) {
            User user = userRepository.findById(openerId).get();
            return accountRepository.save(createDefaultAccount(user));
        }
        throw new IllegalArgumentException();
    }

    private Account lowerBalance(float amount, Long accountId) {
        Account account = accountRepository.getById(accountId);
        account.setBalance(account.getBalance() - amount);
        if (account.getBalance() < 0) {
            throw new IllegalStateException("Balance below zero");
        }
        return accountRepository.save(account);
    }

    private Account createDefaultAccount(User user) {
        Account newAccount = new Account();
        newAccount.setUser(user);
        newAccount.setOpenDate(LocalDateTime.now());
        newAccount.setBalance(0);
        return newAccount;
    }
}
