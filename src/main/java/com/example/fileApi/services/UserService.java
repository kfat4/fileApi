package com.example.fileApi.services;

import com.example.fileApi.model.User;

import java.util.List;

public interface UserService {

     User getUser(Long id);
     User getUserByName(String name);

     List<User> getUsers();
}
