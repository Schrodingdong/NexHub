package com.schrodingdong.resourcemanager.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;


@Getter @Setter @ToString
public class ResourceModel {
    private String resourceName;
    private String resourceDescription;
    private String resourceBucketId;
    private String resourceVisibility;

    public ResourceModel(String resourceName, String resourceDescription, String resourceVisibility){
        this.resourceName = resourceName;
        this.resourceDescription = resourceDescription;
        this.resourceVisibility = resourceVisibility;
        // generated
        this.resourceBucketId = resourceBucketIdGenerator(resourceName);
    }

    private String resourceBucketIdGenerator(String resourceName){
        String extension = resourceName.substring(resourceName.indexOf('.')+1);
        String resName = resourceName.substring(0,resourceName.indexOf('.'));
        resName = resName.replaceAll("\\s+","_");
        // Salt generation
        byte[] array = new byte[15];
        new Random().nextBytes(array);
        String salt = new String(array, StandardCharsets.UTF_8);
        // result string
        String result = resName+salt;
        result = resName+"-"+Math.abs(result.hashCode())+"."+extension;
        return result;
    }
}
