package com.nexhub.databasemanager.repository;

import com.nexhub.databasemanager.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends Neo4jRepository<User, String> {
    Mono<User> findUserByUsername(String username);
}
