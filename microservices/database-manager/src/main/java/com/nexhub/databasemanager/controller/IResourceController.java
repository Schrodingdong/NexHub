package com.nexhub.databasemanager.controller;

import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.model.User;

import java.util.List;

public interface IResourceController {
    Resource addResourceForUser(Resource resource,long userId);
    Resource getResource(long resId);
    List<Resource> getAllResources();
    List<Resource> getAllResourcesOfName(String name);
    List<Resource> getALlResourcesFromUser(long userId);
    List<Resource> getAllPublicResourcesFromUser(long userId);
    Resource updateResourceForUser(long resId, Resource resource);
    void deleteResourceFromUser(long resId);
    void deleteAll();
}
