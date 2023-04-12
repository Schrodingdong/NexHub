package com.nexhub.databasemanager.controller;

import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.model.User;

import java.util.List;

public interface IResourceController {
    Resource addResourceToUser(Resource resource, long userId);
    Resource getResource(long resId);
    List<Resource> getAllResources();
    List<Resource> getAllResourcesOfName(String name);
    Resource updateResourceForUser(long resId, Resource resource, long userId);
    void deleteResourceFromUser(long resId);
    void deleteAll();
}
