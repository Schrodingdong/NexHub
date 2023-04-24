package com.schrodingdong.resourcemanager.model;

import lombok.Data;

@Data
public class DownloadObjectParams {
    String objectName;
    String fileName;
    String bucketName;
}
