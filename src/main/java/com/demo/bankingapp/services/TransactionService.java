package com.demo.bankingapp.services;

import com.demo.bankingapp.entities.DTOs.TransactionDTO;
import com.demo.bankingapp.entities.Transaction;

import java.util.List;

public interface TransactionService {

    public Transaction markTransaction(TransactionDTO transaction);

    public List<Transaction> getTransactionsHistoryByYearAndMonth(String date, Long accountId);
}
