package com.example.fileApi.services;

import com.example.fileApi.model.File;
import com.example.fileApi.model.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

     File getFile(Long id);
     List<File> getFiles();
     FileResponse save(MultipartFile file);
     FileResponse update(Long id, MultipartFile file);
     void deleteFile(Long id);
     byte [] getFileContent(Long id);
}
