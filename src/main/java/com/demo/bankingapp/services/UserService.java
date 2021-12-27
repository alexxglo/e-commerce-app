package com.demo.bankingapp.services;

import com.demo.bankingapp.entities.DTOs.UserDTO;
import com.demo.bankingapp.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    public List<User> getUsers();

    public User registerUser(UserDTO user);
}
