package com.nexhub.databasemanager.repository;

import com.nexhub.databasemanager.model.ResVisibility;
import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {
    @Query("MERGE (u:User {username: $username, email:$email, firstName:$firstName, lastName:$lastName, bucketId:$bucketId}) RETURN u")
    User saveToGraph(@Param("username")String username,
                     @Param("email") String email,
                     @Param("firstName") String firstName,
                     @Param("lastName") String lastName,
                     @Param("bucketId") String bucketId);
    @Query("MATCH (u:User) " +
            "WHERE ID(u) = $userId " +
            "SET u.username=$newUsername " +
            "SET u.firstName=$newFirstName " +
            "SET u.lastName=$newLastName " +
            "SET u.email=$newEmail RETURN u")
    User updateUser(@Param("userId") long userId,
                    @Param("newUsername") String newUsername,
                    @Param("newFirstName") String newFirstName,
                    @Param("newLastName") String newLastName,
                    @Param("newEmail")String newEmail);
    @Query("MATCH (u:User {username: $name}) RETURN u")
    List<User> getUsersByName(@Param("name") String name);
    @Query("MATCH (u:User {email: $email}) RETURN count(u) > 0")
    boolean isMailTaken(@Param("email") String email);
    @Query("MATCH (u:User {email: $email}) RETURN u")
    User getUserByMail(@Param("email") String email);
    @Query("MATCH (u:User) WHERE ID(u) = $userId RETURN u")
    User findByUserId(@Param("userId") long userId);
    @Query("MATCH (u:User) return u")
    List<User> findAllUsers();

    @Query("MATCH (u:User) " +
            "MATCH (u_toFollow:User) " +
            "WHERE ID(u) = $userId and ID(u_toFollow) = $toFollowId " +
            "MERGE (u)-[:FOLLOWS]->(u_toFollow)")
    void followUser(@Param("userId") long userId, @Param("toFollowId") long toFollowId);
    @Query( "MATCH (u:User)-[r:FOLLOWS]->(a:User) " +
            "WHERE ID(u) = $userId and ID(a) = $toUnfollowId " +
            "DELETE r")
    void unfollowUser(@Param("userId") long userId, @Param("toUnfollowId") long toUnfollowId);
    @Query( "MATCH (u:User)<-[:FOLLOWS]-(followers:User) " +
            "WHERE ID(u) = $userId " +
            "RETURN followers")
    List<User> userFollowers(@Param("userId") long userId);
    @Query( "MATCH (u:User)-[:FOLLOWS]->(followings:User) " +
            "WHERE ID(u) = $userId " +
            "RETURN followings")
    List<User> userFollowing(@Param("userId") long userId);

}
