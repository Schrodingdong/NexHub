package com.nexhub.databasemanager.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
@Getter @Setter @ToString
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
    public void addHolder(User u){
        resourceHolders.add(u);
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
