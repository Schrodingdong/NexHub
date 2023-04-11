package com.nexhub.databasemanager.repository;

import com.nexhub.databasemanager.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {
    @Query("MATCH (u:User {username: $name}) RETURN u")
    List<User> getUsersByName(@Param("name") String name);
    @Query("MATCH (u:User {mail: $mail}) RETURN count(u) > 0")
    boolean isMailTaken(@Param("mail") String mail);

}
