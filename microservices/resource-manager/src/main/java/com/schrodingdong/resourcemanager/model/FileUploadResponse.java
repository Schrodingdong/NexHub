package com.schrodingdong.resourcemanager.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadResponse {
    private String fileName;
    private long size;
}