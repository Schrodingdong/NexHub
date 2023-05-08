package com.nexhub.databasemanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nexhub.databasemanager.model.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface IResourceController {
    ResponseEntity<?> addResourceForUser(Resource resource, long userId);
    ResponseEntity<?> getResource(long resId);
    ResponseEntity<?> getAllResources() throws JsonProcessingException;
    ResponseEntity<?> getAllResourcesOfName(String name) throws JsonProcessingException;
    ResponseEntity<?> getALlResourcesFromUser(long userId) throws JsonProcessingException;
    ResponseEntity<?> getAllPublicResourcesFromUser(long userId) throws JsonProcessingException;
    ResponseEntity<?> updateResourceForUser(long resId, Resource resource);
    ResponseEntity<?> deleteResource(long resId);
    ResponseEntity<?> deleteAll();
    ResponseEntity<?> deleteAllResourcesOdUser(long userId);
}
