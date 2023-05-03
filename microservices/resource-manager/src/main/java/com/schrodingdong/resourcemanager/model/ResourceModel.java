package com.schrodingdong.resourcemanager.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.nio.charset.Charset;
import java.util.Random;


@Getter @Setter @ToString
public class ResourceModel {
    private long resourceId;
    @NotNull
    private String resourceName;
    @NotNull
    private String resourceDescription;
    @NotNull
    private String resourceBucketId;
    @NotNull
    private String resourceVisibility;

    public ResourceModel(String resourceName, String resourceDescription, String resourceVisibility) {
        this.resourceName = resourceName;
        this.resourceDescription = resourceDescription;
        this.resourceBucketId = resourceBucketIdGenerator();
        this.resourceVisibility = resourceVisibility;
    }

    private String resourceBucketIdGenerator(){
        String extension = this.resourceName.substring(this.resourceName.indexOf('.')+1);
        String resName = this.resourceName.substring(0,this.resourceName.indexOf('.'));
        resName = resName.replaceAll("\\s+","_");
        // Salt generation
        byte[] array = new byte[15];
        new Random().nextBytes(array);
        String salt = new String(array, Charset.forName("UTF-8"));
        // result string
        String result = resName+salt;
        result = resName+"-"+Math.abs(result.hashCode())+"."+extension;
        return result;
    }
}
