package com.example.fileApi.controllers;

import com.example.fileApi.model.File;
import com.example.fileApi.services.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/files")
@AllArgsConstructor
public class FileController {


    private final FileService fileService;


    @GetMapping
    public ResponseEntity<List<File>> getAllFiles(){
        return ResponseEntity.ok(fileService.getFiles());
    }

    @GetMapping("{id}")
    public ResponseEntity<File> getFile(@PathVariable("id")  Long id){
        return ResponseEntity.ok(fileService.getFile(id));
    }



}
