package com.example.fileApi.controllers;

import com.example.fileApi.model.File;
import com.example.fileApi.model.FileResponse;
import com.example.fileApi.services.FileService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/files")
@AllArgsConstructor
@Log4j2
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

    @ResponseBody
    @PostMapping(value = "/upload", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<FileResponse> uploadFile(@RequestParam("file") MultipartFile file , @RequestParam("userId") Long userId) {
        return new ResponseEntity<>(fileService.save(file ,userId), HttpStatus.CREATED);
    }

    @PutMapping("/updateFile/{id}")
    public ResponseEntity<FileResponse> updateFile(@PathVariable("id")  Long id, @RequestBody MultipartFile file) {
        return new ResponseEntity<>(fileService.update(id,file), HttpStatus.OK);
    }

    @GetMapping("/fileContent/{id}")
    public ResponseEntity<ByteArrayResource> getFileContent(@PathVariable("id")  Long id){

        byte[] array = fileService.getFileContent(id);

        ByteArrayResource resource = new ByteArrayResource(array);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("whatever")
                                .build().toString())
                .body(resource);

//        return ResponseEntity.ok(fileService.getFileContent(id));
    }



}
