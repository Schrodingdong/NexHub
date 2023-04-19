package com.nexhub.databasemanager.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;


@Node
public class Resource {
    @Id
    @GeneratedValue
    private long resourceId;
    @NotBlank
    @NotNull
    private String resourceName;
    @NotBlank
    @NotNull
    private String resourceDescription;
    private String resourceBucketId;
    @NotBlank
    @NotNull
    private String resourceVisibility;

//    @Relationship(type = "OF_CATEGORY", direction = OUTGOING)
//    private Set<Category> resourceCategory = new HashSet<>();
    @Relationship(type = "HAS_A", direction = INCOMING )
    private Set<User> resourceHolders = new HashSet<>();

    public Resource(String resourceName, String resourceDescription,String resourceVisibility) {
        this.resourceName = resourceName;
        this.resourceDescription = resourceDescription;
        this.resourceBucketId = resourceBucketIdGenerator();
        this.resourceVisibility = resourceVisibility;
    }

    private String resourceBucketIdGenerator(){
        String resName = this.resourceName;
        resName = resName.replaceAll("\\s+","_");
        // Salt generation
        byte[] array = new byte[15];
        new Random().nextBytes(array);
        String salt = new String(array, Charset.forName("UTF-8"));
        // result string
        String result = resName+salt;
        result = resName+"-"+Math.abs(result.hashCode());
        return result;
    }
    public void addHolder(User u){
        resourceHolders.add(u);
    }

    public long getResourceId() {
        return resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getResourceBucketId() {
        return resourceBucketId;
    }

    public String getResourceVisibility() {
        return resourceVisibility;
    }

    public Set<User> getResourceHolders() {
        return resourceHolders;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setResourceVisibility(String resourceVisibility) {
        this.resourceVisibility = resourceVisibility;
    }

    public void setResourceBucketId(String resourceBucketId) {
        this.resourceBucketId = resourceBucketId;
    }

    public void setResourceHolders(Set<User> resourceHolders) {
        this.resourceHolders = resourceHolders;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "resourceId=" + resourceId +
                ", resourceName='" + resourceName + '\'' +
                ", resBucketId='" + resourceBucketId + '\'' +
                ", resVisibility=" + resourceVisibility +
                ", resourceHolders=" + resourceHolders +
                '}';
    }

    public String getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }
}
