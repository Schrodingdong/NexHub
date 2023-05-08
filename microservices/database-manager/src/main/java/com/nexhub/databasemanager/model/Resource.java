package com.nexhub.databasemanager.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
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
    @NotBlank
    @NotNull
    private String resourceVisibility;
    @NotBlank
    @NotNull
    private String resourceBucketId;
    private long resourceHolderId;
}
