package com.nexhub.databasemanager.service;

import com.nexhub.databasemanager.exception.BadRequestException;
import com.nexhub.databasemanager.model.ResVisibility;
import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.model.User;
import com.nexhub.databasemanager.repository.ResourceRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {
    @Autowired
    private final ResourceRepository resourceRepository;
    @Autowired
    private final UserService userService;

    public ResourceService(ResourceRepository resourceRepository, UserService userService) {
        this.resourceRepository = resourceRepository;
        this.userService = userService;
    }


    public Resource getResourceById(@NotNull long id){
        Optional<Resource> resOptional = resourceRepository.findById(id);
        return resOptional.orElse(null);
    }
    public List<Resource> getResourcesByName(String name){
        return resourceRepository.getResourceByName(name);
    }
    public List<Resource> getAllResources(){
        return resourceRepository.findAll();
    }
    public List<Resource> getAllResourcesFromUser(@NotNull long userId){
        if (userService.userExists(userId) == false)
            return null;
        return resourceRepository.getAllResourcesFromUser(userId);
    }
    public Resource saveResource(@NotNull long userId, @NotNull Resource r){
        if (userService.userExists(userId)){
            resourceRepository.save(r);
            resourceRepository.linkToUser(r.getResourceId(), userId);
            return r;
        }
        return null;
    }
    public Resource updateResource(@NotNull long resId,@NotNull Resource modifiedResource,@NotNull long userId) throws BadRequestException{
        if (modifiedResource == null){
            throw new BadRequestException("No Input Data");
        }
        Resource selectedRes = getResourceById(resId);
        if(selectedRes != null){
            String resourceName = modifiedResource.getResourceName();
            ResVisibility visibility = modifiedResource.getResVisibility();

            selectedRes.setResVisibility(
                    (visibility == null)?
                            selectedRes.getResVisibility() :
                            visibility
            );
            selectedRes.setResourceName(
                    (resourceName == null || resourceName.isEmpty())?
                            selectedRes.getResourceName() :
                            resourceName
            );

            return saveResource(userId, modifiedResource);
        } else {
            return saveResource(userId, modifiedResource);
        }
    }

    public void deleteResource(@NotNull long resId){
        resourceRepository.deleteResourceById(resId);
    }
    public void deleteAll(){
        resourceRepository.deleteAll();
    }


}
