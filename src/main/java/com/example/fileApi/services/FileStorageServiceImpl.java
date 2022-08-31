package com.example.fileApi.services;

import com.example.fileApi.handler.FileStorageException;
import com.example.fileApi.handler.UnSupportedFileTypeException;
import com.example.fileApi.model.File;
import com.example.fileApi.model.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {


    private Path fileStorageLocation;


    @Autowired
    public void FileStorageService() {

        fileStorageLocation = Paths.get("file-repository");
        if (!Files.exists(fileStorageLocation)) {
            try {
                Files.createDirectories(fileStorageLocation);
            } catch (IOException e) {
                throw new FileStorageException("Could not create 'file-repository' directory ", e);
            }
        }
    }

    @Override
    public File storeFile(MultipartFile multipartFile, Long userId) {

        String multipartFileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        String storageFileName = new StringBuilder(userId.toString()).append("-").append(multipartFileName).toString();

        String type = Objects.requireNonNull(multipartFile.getContentType()).split("/")[1];

        if (!checkFileType(type)) {
            throw new UnSupportedFileTypeException("Unsupported file type " + storageFileName);
        }

        try {
            if (storageFileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + storageFileName);
            }

            Path targetLocation = fileStorageLocation.resolve(storageFileName);

            Files.copy(multipartFile.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);


            File file = new File();
            file.setName(multipartFileName);
            file.setStorageName(storageFileName);
            file.setType(type);
            file.setSize(multipartFile.getSize());
            String path = editPathSlash(targetLocation.toFile().getAbsolutePath());
            file.setPath(path);

            return file;

        } catch (IOException e) {
            throw new FileStorageException("Could not store file " + storageFileName + ". Please try again!", e);
        }
    }

    @Override
    public File updateFile(MultipartFile multiPartFile, File file) {
        File storedFile = storeFile(multiPartFile, file.getUser().getId());
        storedFile.setId(file.getId());
        deleteFile(file);
        return renameByFileId(storedFile);
    }

    @Override
    public boolean deleteFile(File file) {

        try {
           return Files.deleteIfExists(Paths.get(file.getPath()));
        } catch (IOException e) {
            throw new FileStorageException("Could not delete file " + file.getName() , e);
        }
    }

    @Override
    public byte[] getFileContent(File file) {
        try {
            return Files.readAllBytes(Paths.get(file.getPath()));
        } catch (IOException e) {
            throw new FileStorageException("Could not read file content " + file.getName(), e);
        }

    }

    @Override
    public File renameByFileId(File file) {


        Path source = Paths.get(file.getPath());

        try {
            // for uniq path : fileId-userId-fileName
            String storageName = new StringBuilder(file.getId().toString())
                    .append("-")
                    .append(file.getStorageName()).toString();

            source = Files.move(source, source.resolveSibling(storageName), StandardCopyOption.REPLACE_EXISTING);
            file.setPath(editPathSlash(source.toFile().getAbsolutePath()));
            file.setStorageName(storageName);
//            file.setName(source.toFile().getName());
        } catch (IOException e) {
            throw new FileStorageException("Could not rename file name " + file.getName(), e);
        }
        return file;
    }

    private boolean checkFileType(String type) {
        return (FileType.findByValue(type) != null);
    }

    private String editPathSlash(String path) {
        return path.replaceAll("\\\\", "/");
    }


}
