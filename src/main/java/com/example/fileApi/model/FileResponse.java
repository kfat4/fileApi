package com.example.fileApi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class FileResponse {

    private Long id;
    private String fileName;
    private String filePath;
    private String fileType;
    private long size;
}
