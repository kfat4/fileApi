package com.example.fileApi.services;

import com.example.fileApi.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

     User getUser(Long id);
     Optional<User> getUserByName(String name);

     List<User> getUsers();
}
