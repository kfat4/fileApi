package com.example.fileApi.controllers;

import com.example.fileApi.model.File;
import com.example.fileApi.model.FileResponse;
import com.example.fileApi.services.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @DeleteMapping("{id}")
    public ResponseEntity deleteFile(@PathVariable("id")  Long id){
        fileService.deleteFile(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/uploadFile")
    @ResponseBody
    public ResponseEntity<FileResponse> uploadFile(@RequestBody MultipartFile file) {
        return new ResponseEntity<>(fileService.save(file), HttpStatus.CREATED);
    }

    @PutMapping("/updateFile/{id}")
    public ResponseEntity<FileResponse> updateFile(@PathVariable("id")  Long id, @RequestBody MultipartFile file) {
        return new ResponseEntity<>(fileService.update(id,file), HttpStatus.OK);
    }

    @GetMapping("/fileContent/{id}")
    public ResponseEntity<byte []> getFileContent(@PathVariable("id")  Long id){
        return ResponseEntity.ok(fileService.getFileContent(id));
    }



}
