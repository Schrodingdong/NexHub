package com.nexhub.databasemanager.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static org.springframework.data.neo4j.core.schema.Relationship.Direction.INCOMING;
import static org.springframework.data.neo4j.core.schema.Relationship.Direction.OUTGOING;

@Node
@Getter @Setter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private long userId;
    @NotBlank
    @NotNull
    private String username;
    @NotBlank
    @NotNull
    private String firstName;
    @NotBlank
    @NotNull
    private String lastName;
    @Email
    @NotNull
    private String email;
    @NotBlank
    @NotNull
    private String bucketId;
}
