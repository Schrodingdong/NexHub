package com.nexhub.databasemanager.repository;

import com.nexhub.databasemanager.model.Category;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CategoryRepository extends Neo4jRepository<Category, String> {
}
