package com.example.fileApi.controllers;

import com.example.fileApi.model.User;
import com.example.fileApi.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllFiles(){
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("{id}")
    public ResponseEntity<User> getFile(@PathVariable("id")  Long id){
        return ResponseEntity.ok(userService.getUser(id));
    }

}
