package com.schrodingdong.resourcemanager.model;

import lombok.Data;

@Data
public class DeleteObjectParams {
    String objectName;
    String bucketName;
}
