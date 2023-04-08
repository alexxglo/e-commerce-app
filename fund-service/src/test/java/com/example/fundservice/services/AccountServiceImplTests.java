package com.example.fundservice.services;


import com.example.fundservice.entities.Account;
import com.example.fundservice.entities.DTOs.TransactionDTO;
import com.example.fundservice.repositories.AccountRepository;
import com.example.fundservice.services.impl.AccountServiceImpl;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountServiceImplTests {
    @Autowired
    private AccountServiceImpl accountService;
    @Mock
    private AccountRepository accountRepository;
    private Long mockedId = 1L;

    /*

    Intrare amount A, account balance B, account id S

    Domeniul pentru amount
    A1 = { a | a <= 0 }
    A2 = { a | a > 0 && a <=100000 }
    A3 = { a | a > 100000 }

    Domeniul pentru account balance
    B1 = { b | b >= a }
    B2 = { b | b < a }

    Domeniul pentru account id
    S1 = { s | s se gaseste in accountRepository }
    S2 = { s | s nu se gaseste in accountRepository }

    Iesiri
    I1 = {"Value below zero"}
    I2 = {"Amount over max"}
    I3 = {"OK"}
    I4 = {"Balance below zero"}
    I5 = {"User not found"}

    Clasele
    C11 = { (a, b, s) | a in A1, s in S1 } -> I1
    C31 = { (a, b, s) | a in A3, s in S1 } -> I2
    C211 = { (a, b, s) | a in A2, b in B1, s in S1 } -> I3
    C221 = { (a, b, s) | a in A2, b in B2, s in S1 } -> I4
    C2 = { (a, b, s) | s in S2 } -> I5

    Equivalence partitioning
     */
    @ParameterizedTest
    @CsvSource({
            "-10, 10, true",
            "234567, 120, true",
            "100, 200, true",
            "344, 144, true",
            "80, 50, false",

    })
    @Transactional
    void verifyAmountEqPart(int amount, float balance, boolean idInAccountRepo) {
        TransactionDTO transaction = mock(TransactionDTO.class);
        Account account = mock(Account.class);
        if (idInAccountRepo) {
            when(transaction.getSenderId()).thenReturn(1L);
            when(transaction.getReceiverId()).thenReturn(2L);
            when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        }


        when(transaction.getAmount()).thenReturn(amount);
        when(accountRepository.getById(any())).thenReturn(account);
        when(account.getBalance()).thenReturn(balance);

        if (amount == -10) {
            assertThatThrownBy(() -> accountService.transferFunds(transaction)).hasMessage("Value below zero");
        } else if (amount == 234567) {
            assertThatThrownBy(() -> accountService.transferFunds(transaction)).hasMessage("Amount over max");
        } else if (amount == 80) {
            assertThatThrownBy(() -> accountService.transferFunds(transaction)).hasMessage("User not found");
        } else if (amount == 344) {
            assertThatThrownBy(() -> accountService.transferFunds(transaction)).hasMessage("Balance below zero");
        } else {
            accountService.transferFunds(transaction);
        }
    }

    // Boundary value analysis
    @ParameterizedTest
    @CsvSource({
            "0, -1, true",
            "0, -1, false",
            "0, 1, true",
            "0, 1, false",
            "1, 0, true",
            "1, 0, false",
            "1, 2, false",
            "99999, 99998, false",
            "99999, 100000, true",
            "99999, 100000, false",
            "100000, 99999, true",
            "100000, 99999, false",
            "100000, 100001, true",
            "100000, 100001, false",
    })
    @Transactional
    void verifyAmountBoundVal(int amount, float balance, boolean idInAccountRepo) {
        TransactionDTO transaction = mock(TransactionDTO.class);
        Account account = mock(Account.class);
        if (idInAccountRepo) {
            when(transaction.getSenderId()).thenReturn(1L);
            when(transaction.getReceiverId()).thenReturn(2L);
            when(transaction.getAmount()).thenReturn(amount);
            when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        }

        if (amount < balance && idInAccountRepo && amount > 0 && amount < 100000) {
            when(accountRepository.getById(any())).thenReturn(account);
            when(account.getBalance()).thenReturn(-1f);
            assertThatThrownBy(() -> accountService.transferFunds(transaction)).hasMessage("Balance below zero");
        }
        else if (!idInAccountRepo) {
            assertThatThrownBy(() -> accountService.transferFunds(transaction)).hasMessage("User not found");
        }
        else if (amount == 0) {
            assertThatThrownBy(() -> accountService.transferFunds(transaction)).hasMessage("Value below zero");
        } else if (amount == 100000) {
            assertThatThrownBy(() -> accountService.transferFunds(transaction)).hasMessage("Amount over max");
        }
        else {
            accountService.transferFunds(transaction);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "-1345, 125, true",
            "234567, 1220, true",
            "1030, 205, true",
            "107, 20, false",
    })
    @Transactional
    void verifyAmountGce(int amount, float balance, boolean idInAccountRepo) {
        TransactionDTO transaction = mock(TransactionDTO.class);
        Account account = mock(Account.class);
        if (idInAccountRepo) {
            when(transaction.getSenderId()).thenReturn(1L);
            when(transaction.getReceiverId()).thenReturn(2L);
            when(accountRepository.findById(any())).thenReturn(Optional.of(account));
        }


        when(transaction.getAmount()).thenReturn(amount);
        when(accountRepository.getById(any())).thenReturn(account);
        when(account.getBalance()).thenReturn(balance);

        if (amount == -1345) {
            assertThatThrownBy(() -> accountService.transferFunds(transaction)).hasMessage("Value below zero");
        } else if (amount == 234567) {
            assertThatThrownBy(() -> accountService.transferFunds(transaction)).hasMessage("Amount over max");
        } else if (amount == 107) {
            assertThatThrownBy(() -> accountService.transferFunds(transaction)).hasMessage("User not found");
        } else if (amount == 1030) {
            assertThatThrownBy(() -> accountService.transferFunds(transaction)).hasMessage("Balance below zero");
        } else {
            accountService.transferFunds(transaction);
        }
    }
}
