package com.example.fundservice.services.impl;

import com.example.fundservice.entities.DTOs.ProductResponse;
import com.example.fundservice.entities.DTOs.TransactionDTO;
import com.example.fundservice.entities.Transaction;
import com.example.fundservice.repositories.TransactionRepository;
import com.example.fundservice.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
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
    public List<Transaction> getTransactionsHistoryByYearAndMonth(String date, Long accountId) {

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("MMM yyyy")
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .toFormatter();
        LocalDate dateTime = LocalDate.parse(date, formatter);
        LocalDateTime endDate = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), 28, 0, 0).with(TemporalAdjusters.lastDayOfMonth());
        LocalDateTime startDate = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), 1, 0, 0).with(TemporalAdjusters.firstDayOfMonth());
        return transactionRepository.getTransactionByMonthAndYear(startDate, endDate, accountId);
    }

    @Override
    public ResponseEntity<TransactionDTO> addProductToHistory(Long accountNumber, ProductResponse productResponse) {
        try {
            TransactionDTO transactionFromEcomm = ecommerceToTransaction(accountNumber, productResponse);
            Transaction newTransaction = addTransaction(transactionFromEcomm);
            transactionRepository.save(newTransaction);
            return new ResponseEntity<>(transactionFromEcomm, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<TransactionDTO> getHistoryOfAccount(Long accountNumber) {
        List<TransactionDTO> transactionsList = transactionsToTransactionDTOs(accountNumber);
        return transactionsList;
    }

    private Transaction addTransaction(TransactionDTO transactionRequest) {
        Transaction transaction = new Transaction();
        transaction.setTransactionTime(LocalDateTime.now());
        transaction.setAmount(transactionRequest.getAmount());
        transaction.setMessage(transactionRequest.getMessage());
        transaction.setReceiverAccountId(transactionRequest.getReceiverId());
        transaction.setSenderAccountId(transactionRequest.getSenderId());
        return transaction;
    }

    private TransactionDTO ecommerceToTransaction(Long accountNumber, ProductResponse productResponse) {
        TransactionDTO transactionFromEcomm = new TransactionDTO();
        transactionFromEcomm.setReceiverId(0l);
        transactionFromEcomm.setSenderId(accountNumber);
        transactionFromEcomm.setAmount(productResponse.getPrice());
        transactionFromEcomm.setMessage(productResponse.getName());
        return transactionFromEcomm;
    }

    private List<TransactionDTO> transactionsToTransactionDTOs(Long accountNumber) {
        List<TransactionDTO> transactionsList = new ArrayList<>();
        List<Transaction> transactionsFromRepo = transactionRepository.getAllBySenderAccountId(accountNumber);
        for (Transaction transaction : transactionsFromRepo) {
            TransactionDTO transactionDTO = new TransactionDTO();
            transactionDTO.setReceiverId(transaction.getReceiverAccountId());
            transactionDTO.setSenderId(transaction.getSenderId());
            transactionDTO.setMessage(transaction.getMessage());
            transactionDTO.setAmount(transaction.getAmount());
            transactionsList.add(transactionDTO);
        }
        return transactionsList;
    }
}
