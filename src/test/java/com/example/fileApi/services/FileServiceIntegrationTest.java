package com.example.fileApi.services;

import com.example.fileApi.FileApiApplication;
import com.example.fileApi.config.H2JpaConfig;
import com.example.fileApi.handler.FileStorageException;
import com.example.fileApi.model.File;
import com.example.fileApi.model.FileResponse;
import com.example.fileApi.repositories.FileRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {FileApiApplication.class, H2JpaConfig.class})
@RunWith(SpringRunner.class)
@Transactional
@ActiveProfiles("test")
public class FileServiceIntegrationTest {

    @Autowired
    private  FileRepository fileRepository;
    @Autowired
    private  FileStorageService fileStorageService;

    @Autowired
    private FileService fileService;

    @Test
    public void save(){


        String fileName = "hello.png";

        MockMultipartFile mockMultipartFile
                = new MockMultipartFile(
                "file",
                fileName,
                MediaType.IMAGE_PNG_VALUE,
                "Hello, World!".getBytes()
        );

        FileResponse fileResponse = fileService.save(mockMultipartFile, 1L);
        Assert.assertNotNull(fileResponse);
        Assert.assertEquals(fileName,fileResponse.getFileName());

        File file = fileRepository.findById(fileResponse.getId()).get();

        Assert.assertNotNull(file);
        Assert.assertEquals(fileResponse.getId() , file.getId());

        byte[] fileContent = fileStorageService.getFileContent(file);

        Assert.assertNotNull(fileContent);
        Assert.assertTrue(fileContent.length >0);

        Assert.assertTrue(fileStorageService.deleteFile(file));

    }

    @Test
    public void update(){

        String fileName = "test.png";

        MockMultipartFile mockMultipartFile
                = new MockMultipartFile(
                "file",
                fileName,
                MediaType.IMAGE_PNG_VALUE,
                "Hello, World!".getBytes()
        );

        FileResponse fileResponse = fileService.save(mockMultipartFile, 1L);
        final File file = fileRepository.findById(fileResponse.getId()).get();

        String updateFileName = "test-update.png";

        MockMultipartFile mockMultipartFileUpdate
                = new MockMultipartFile(
                "file",
                updateFileName,
                MediaType.IMAGE_PNG_VALUE,
                "Hello, World!".getBytes()
        );


        FileResponse updateFileResponse = fileService.update(file.getId(), mockMultipartFileUpdate);
        File updatedFile = fileRepository.findById(updateFileResponse.getId()).get();

        Assert.assertNotEquals(fileResponse.getFilePath() , (updatedFile.getPath()));
        Assert.assertEquals(updateFileName , updatedFile.getName());
        boolean isFileExist = new java.io.File(updatedFile.getPath()).exists();
        Assert.assertTrue(isFileExist);

        fileService.deleteFile(updatedFile.getId());
    }

    @Test
    public void delete(){

        String fileName = "test-delete.png";

        MockMultipartFile mockMultipartFile
                = new MockMultipartFile(
                "file",
                fileName,
                MediaType.IMAGE_PNG_VALUE,
                "Hello, World!".getBytes()
        );

        FileResponse fileResponse = fileService.save(mockMultipartFile, 1L);
        File file = fileRepository.findById(fileResponse.getId()).get();

       fileService.deleteFile(file.getId());

        Exception exception = assertThrows(FileStorageException.class, () -> fileStorageService.getFileContent(file));

        assertThrows(EntityNotFoundException.class, () -> fileService.getFile(file.getId()));


        String expectedMessage = "Could not read file content "+ file.getName();
        String actualMessage = exception.getMessage();

        Assert.assertTrue(actualMessage.contains(expectedMessage));


    }


}
