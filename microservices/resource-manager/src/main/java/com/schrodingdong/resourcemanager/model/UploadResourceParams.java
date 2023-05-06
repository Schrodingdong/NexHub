package com.schrodingdong.resourcemanager.model;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadResourceParams {
    @NotNull
    MultipartFile fileToUpload;
    @NotNull
    String userMail;
    @NotNull
    String resourceDescription;
    @NotNull
    String resourceVisibility;

    public String getFileName(){
        return fileToUpload.getOriginalFilename();
    }
    public long getSize(){
        return fileToUpload.getSize();
    }
}
