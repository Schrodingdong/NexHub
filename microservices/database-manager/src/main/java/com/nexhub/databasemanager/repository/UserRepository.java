package com.nexhub.databasemanager.repository;

import com.nexhub.databasemanager.model.ResVisibility;
import com.nexhub.databasemanager.model.Resource;
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
    @Query("MERGE (u:User {username: $username, mail:$mail}) RETURN u")
    User saveToGraph(@Param("username")String username, @Param("mail") String mail);
    @Query("MATCH (u:User) WHERE ID(u) = $userId SET u.username=$newUsername SET u.mail=$newMail RETURN u")
    User updateUser(@Param("userId") long userId, @Param("newUsername") String newUsername, @Param("newMail")String newMail);
    @Query("MATCH (u:User {username: $name}) RETURN u")
    List<User> getUsersByName(@Param("name") String name);
    @Query("MATCH (u:User {mail: $mail}) RETURN count(u) > 0")
    boolean isMailTaken(@Param("mail") String mail);
    @Query("MATCH (u:User {mail: $mail} RETURN u")
    User getUserByMail(@Param("mail") String mail);
    @Query("MATCH (u:User) WHERE ID(u) = $userId RETURN u")
    User findByUserId(@Param("userId") long userId);
    @Query("MATCH (u:User) return u")
    List<User> findAllUsers();

    @Query("MATCH (u:User) " +
            "MATCH (u_toFollow:User) " +
            "WHERE ID(u) = $userId and ID(u_toFollow) = $toFollowId " +
            "MERGE (u)-[:FOLLOWS]->(u_toFollow)")
    void followUser(@Param("userId") long userId, @Param("toFollowId") long toFollowId);
    @Query( "MATCH (u:User)<-[:FOLLOWS]-(followers:User) " +
            "WHERE ID(u) = $userId " +
            "RETURN followers")
    List<User> userFollowers(@Param("userId") long userId);
    @Query( "MATCH (u:User)-[:FOLLOWS]->(followings:User) " +
            "WHERE ID(u) = $userId " +
            "RETURN followings")
    List<User> userFollowing(@Param("userId") long userId);

}
