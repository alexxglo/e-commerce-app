package com.demo.bankingapp.services.impl;

import com.demo.bankingapp.entities.DTOs.TransactionDTO;
import com.demo.bankingapp.entities.Transaction;
import com.demo.bankingapp.repositories.TransactionRepository;
import com.demo.bankingapp.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public Transaction markTransaction(TransactionDTO transactionRequest) {
        Transaction transaction = addTransaction(transactionRequest);
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsHistoryByYearAndMonth(String date) {

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("MMM yyyy")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();
        LocalDate dateTime = LocalDate.parse(date, formatter);
        LocalDateTime endDate = LocalDateTime.of(dateTime.getYear(),dateTime.getMonth(),28,0,0).with(TemporalAdjusters.lastDayOfMonth());
        LocalDateTime startDate = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), 1, 0, 0).with(TemporalAdjusters.firstDayOfMonth());
        return transactionRepository.getTransactionByMonthAndYear(startDate, endDate);
}

    public Transaction addTransaction(TransactionDTO transactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setMessage(transactionRequest.getMessage());
        transaction.setReceiverId(transactionRequest.getReceiverId());
        transaction.setSenderId(transactionRequest.getSenderId());
        return transaction;
    }
}
