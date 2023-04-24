package com.schrodingdong.resourcemanager.model;

import lombok.Data;

@Data
public class UploadObjectParams {
    String fileName;
    String resId;
    String bucketName;
}
