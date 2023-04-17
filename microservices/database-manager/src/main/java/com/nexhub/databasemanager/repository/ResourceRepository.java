package com.nexhub.databasemanager.repository;

import com.nexhub.databasemanager.model.Resource;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ResourceRepository extends Neo4jRepository<Resource, Long> {
    @Query( "MATCH (r:Resource {resourceName: $name}) RETURN r")
    List<Resource> getResourceByName(@Param("name") String name);

    @Query("MATCH (u:User) MATCH (r:Resource) WHERE ID(u) = $userId AND ID(r) = $resId MERGE (u)-[:HAS_A]->(r) ")
    void linkToUser(@Param("resId") long resId, @Param("userId") long userId);

    @Query( "MATCH (r:Resource)<-[:HAS_A]-(u:User) " +
            "WHERE ID(u) = $userId " +
            "RETURN r")
    List<Resource> getAllResourcesFromUser(@Param("userId") long userId);

    @Query( "MATCH (r:Resource {resVisibility: 'PUBLIC'})<-[:HAS_A]-(u:User) " +
            "WHERE ID(u) = $userId " +
            "RETURN r")
    List<Resource> getAllPublicResourcesFromUser(@Param("userId") long userId);
}
