package com.example.fileApi.services;

import com.example.fileApi.model.File;

import java.util.List;

public interface FileService {

     File getFile(Long id);
     List<File> getFiles();
     File save(File file);
     File update(Long id,File file);
}
