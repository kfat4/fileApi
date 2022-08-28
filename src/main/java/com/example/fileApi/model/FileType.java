package com.example.fileApi.model;

public enum FileType {

    PDF("pdf"),
    JPEG("jpeg"),
    PNG("png"),
    JPG("jpg"),
    DOCX("docx"),
    XLSX("xlsx");


    private final String type;
    FileType(String type) {
        this.type = type;
    }

    private String getType() { return type; }

    public static FileType findByValue(String value) {
        FileType result = null;
        for (FileType fileType : values()) {
            if (fileType.getType().equalsIgnoreCase(value)) {
                result = fileType;
                break;
            }
        }
        return result;
    }

}
