package com.example.fileApi.services;

import com.example.fileApi.model.User;
import com.example.fileApi.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User getUserByName(String userName) {
        return userRepository.findByName(userName).orElseThrow(EntityNotFoundException::new);
    }
}
