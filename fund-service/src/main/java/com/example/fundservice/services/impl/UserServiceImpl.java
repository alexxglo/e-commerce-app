package com.example.fundservice.services.impl;

import com.example.fundservice.entities.Account;
import com.example.fundservice.entities.DTOs.UserDTO;
import com.example.fundservice.entities.User;
import com.example.fundservice.repositories.AccountRepository;
import com.example.fundservice.repositories.UserRepository;
import com.example.fundservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(UserDTO request) {
        User user = createNewUser(request);
        List<Account> accountList = new ArrayList<>();

        if(areCredentialsOK(user)) {
            Account newAccount = createNewAccount(user);
            accountList.add(newAccount);
            user.setAccountList(accountList);
            userRepository.save(user);
            accountRepository.save(newAccount);
            return user;
        }
        throw new IllegalArgumentException();
    }

    private boolean areCredentialsOK(User user) {
        if(user.getAge() < 18) {
            return false;
        }
        if(!isEmailOK(user.getEmail())) {
            return false;
        }
        return true;
    }

    private boolean isEmailOK(String email){
        Pattern pattern = Pattern.compile("^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    private Account createNewAccount(User user) {
        Account newAccount = new Account();
        newAccount.setUser(user);
        newAccount.setBalance(0);
        newAccount.setOpenDate(LocalDateTime.now());
        return newAccount;
    }

    private User createNewUser(UserDTO request) {
        User user = new User();
        user.setAge(request.getAge());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setUID(request.getUID());
        user.setPhoneNo(request.getPhoneNo());
        user.setAccountList(null);

        return user;
    }
}
