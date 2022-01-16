package com.example.fundservice.services;

import com.example.fundservice.entities.DTOs.UserDTO;
import com.example.fundservice.entities.User;

import java.util.List;

public interface UserService {

    public List<User> getUsers();

    public User registerUser(UserDTO user);
}
