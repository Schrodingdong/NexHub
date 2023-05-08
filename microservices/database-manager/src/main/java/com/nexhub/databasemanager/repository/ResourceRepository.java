package com.nexhub.databasemanager.repository;

import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ResourceRepository extends Neo4jRepository<Resource, Long> {
    @Query("MATCH (r:Resource) WHERE ID(r) = $resId " +
            "SET r.resourceName=$newName " +
            "SET r.resourceDescription=$newDesc " +
            "SET r.resourceVisibility=$newVis " +
            "RETURN r")
    Resource updateResource(@Param("resId") long resId,
                            @Param("newName") String name,
                            @Param("newDesc") String resDesc,
                            @Param("newVis") String vis);
    @Query("MERGE (r:Resource {resourceName: $name, " +
            "resourceDescription: $resDesc, " +
            "resourceBucketId: $resBucketId, " +
            "resourceVisibility: $vis, " +
            "resourceHolderId: $userId}) " +
            "RETURN r")
    Resource saveToGraph(@Param("name") String name,
                         @Param("resDesc") String resDesc,
                         @Param("resBucketId") String resBucketId,
                         @Param("userId") long userId,
                         @Param("vis") String vis);
    @Query( "MATCH (r:Resource {resourceName: $name}) RETURN r")
    List<Resource> getResourceByName(@Param("name") String name);
    @Query("MATCH (r:Resource) WHERE ID(r)=$resId RETURN r")
    Resource getResourceById(@Param("resId") long resId);
    @Query("MATCH (r:Resource) return r")
    List<Resource> getAllResources();
    @Query("MATCH (u:User) MATCH (r:Resource) WHERE ID(u) = $userId AND ID(r) = $resId MERGE (u)-[:HAS_A]->(r) ")
    void linkToUser(@Param("resId") long resId, @Param("userId") long userId);
    @Query( "MATCH (r:Resource)<-[:HAS_A]-(u:User) " +
            "WHERE ID(u) = $userId " +
            "RETURN r")
    List<Resource> getAllResourcesFromUser(@Param("userId") long userId);

    @Query( "MATCH (r:Resource {resourceVisibility: 'PUBLIC'})<-[:HAS_A]-(u:User) " +
            "WHERE ID(u) = $userId " +
            "RETURN r")
    List<Resource> getAllPublicResourcesFromUser(@Param("userId") long userId);
    @Query( "MATCH (r:Resource)<-[:HAS_A]-(u:User) " +
            "WHERE ID(u) = $userId " +
            "DETACH DELETE r")
    void deleteResourcesOfUser(@Param("userId") long userId);
}
