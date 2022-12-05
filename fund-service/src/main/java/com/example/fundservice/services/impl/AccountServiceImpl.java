package com.example.fundservice.services.impl;

import com.example.fundservice.entities.Account;
import com.example.fundservice.entities.DTOs.AccountDTO;
import com.example.fundservice.entities.DTOs.TransactionDTO;
import com.example.fundservice.entities.User;
import com.example.fundservice.repositories.AccountRepository;
import com.example.fundservice.repositories.UserRepository;
import com.example.fundservice.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

        @Override
        public ResponseEntity<AccountDTO> buyProduct(Long accountNumber, float price) {
        try {
            Account foundAccount = lowerBalance(price, accountNumber);
            AccountDTO foundAccountResponse = new AccountDTO();
            foundAccountResponse.setId(foundAccount.getId());
            foundAccountResponse.setBalance(foundAccount.getBalance());
            return new ResponseEntity<>(foundAccountResponse, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Long searchUser(Long accountNumber) {
        Optional<Account> user = accountRepository.findById(accountNumber);
        if(user.isPresent()) {
            return user.get().getUser().getId();
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
