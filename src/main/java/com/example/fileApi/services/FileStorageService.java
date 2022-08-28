package com.example.fileApi.services;

import com.example.fileApi.model.File;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

     File storeFile(MultipartFile file);

     File updateFile(MultipartFile multiPartFile , File file);

     boolean deleteFile(File file);

     byte[] getFileContent(File file);
     File renameByFileId(File file);


}
