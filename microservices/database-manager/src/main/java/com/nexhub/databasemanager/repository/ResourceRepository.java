package com.nexhub.databasemanager.repository;

import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceRepository extends Neo4jRepository<Resource, Long> {
    @Query( "MATCH (r:Resource {resourceName: $name}) RETURN r")
    List<Resource> getResourceByName(@Param("name") String name);

    @Query( "MATCH (r:Resource {resourceId: $resId})" +
            "MATCH (u:User {userId: $userId})" +
            "MERGE(u)-[:HAS_A]->(r)")
    boolean linkToUser(@Param("resId") long resId, @Param("userId") long userId);

    @Query( "MATCH (r:Resource {resourceId: $resId})" +
            "DETACH DELETE (r)")
    Resource deleteResourceById(@Param("resId") long resID);

    @Query( "MATCH (r:Resource)<-[:HAS_A]-(u:user {userId: $userId}" +
            "RETURN r")
    List<Resource> getAllResourcesFromUser(@Param("userId") long userId);

    // TODO implement methods related to CATEGROY
}
