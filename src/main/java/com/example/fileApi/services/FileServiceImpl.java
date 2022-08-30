package com.example.fileApi.services;

import com.example.fileApi.model.File;
import com.example.fileApi.model.FileResponse;
import com.example.fileApi.model.User;
import com.example.fileApi.repositories.FileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileStorageService fileStorageService;

    private final UserService userService;


    @Override
    public File getFile(Long id) {
        return fileRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<File> getFiles() {
        return StreamSupport
                .stream(fileRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public FileResponse save(MultipartFile multipartFile , Long userId) {
        File file = fileStorageService.storeFile(multipartFile,userId);

        User user = userService.getUser(userId);
        file.setUser(user);
        file = fileRepository.save(file);
        file = fileStorageService.renameByFileId(file);
        fileRepository.save(file);

        return new FileResponse(file.getId(),file.getName(), file.getPath(), file.getType(), file.getSize());
    }

    @Override
    public FileResponse update(Long fileId, MultipartFile file) {

        File existingFile = fileRepository.findById(fileId).orElseThrow(EntityNotFoundException::new);

        File updatedFile = fileStorageService.updateFile(file,existingFile);

        existingFile.setName(updatedFile.getName());
        existingFile.setSize(updatedFile.getSize());
        existingFile.setType(updatedFile.getType());
        existingFile.setPath(updatedFile.getPath());
        fileRepository.save(existingFile);
        return new FileResponse(existingFile.getId(),existingFile.getName(), existingFile.getPath(), existingFile.getType(), existingFile.getSize());
    }

    @Override
    public void deleteFile(Long id) {
        File file = fileRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        fileStorageService.deleteFile(file);
        fileRepository.delete(file);
    }

    @Override
    public byte[] getFileContent(Long id) {
        File file = fileRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return  fileStorageService.getFileContent(file);
    }
}
