package com.example.fileApi.services;

import com.example.fileApi.model.User;
import com.example.fileApi.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("No user can be found with this id!"));
    }

    @Override
    public Optional<User> getUserByName(String userName) {
        return userRepository.findByName(userName);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
