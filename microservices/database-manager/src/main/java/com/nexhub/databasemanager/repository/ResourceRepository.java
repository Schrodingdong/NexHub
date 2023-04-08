package com.nexhub.databasemanager.repository;

import com.nexhub.databasemanager.model.Resource;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ResourceRepository extends Neo4jRepository<Resource, String> {
}
