package com.example.fileApi.services;

import com.example.fileApi.model.File;
import com.example.fileApi.repositories.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class FileServiceImpl implements  FileService{

    private final FileRepository fileRepository;

    @Override
    public File getFile(Long id) {
        return fileRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<File> getFiles() {
        return  StreamSupport
                .stream(fileRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public File save(File file) {
        return fileRepository.save(file);
    }

    @Override
    public File update(Long id , File file) {
        File existingFile = fileRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        existingFile.setName(file.getName());
        existingFile.setSize(file.getSize());
        existingFile.setExtension(file.getExtension());
        existingFile.setPath(file.getPath());
        return fileRepository.save(existingFile);
    }
}
