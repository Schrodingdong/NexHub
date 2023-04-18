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

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    public Resource getResourceById(@NotNull long id){
        Resource res = resourceRepository.getResourceById(id);
        return res;
    }
    public List<Resource> getResourcesByName(String name){
        return resourceRepository.getResourceByName(name);
    }
    public List<Resource> getAllResources(){
        return resourceRepository.getAllResources();
    }
    public List<Resource> getAllResourcesFromUser(long userId){
        return resourceRepository.getAllResourcesFromUser(userId);
    }
    public List<Resource> getAllPublicResourcesFromUser(long userId){
        return resourceRepository.getAllPublicResourcesFromUser(userId);
    }
    public Resource saveResourceForUser(@NotNull Resource r, @NotNull long userId){
        r = resourceRepository.saveToGraph(r.getResourceName(), r.getResourceDescription(), r.getResBucketId(), r.getResVisibility());
        resourceRepository.linkToUser(r.getResourceId(), userId);
        return r;
    }
    public Resource updateResource (
            @NotNull long resId,
            @NotNull Resource modifiedResource) throws BadRequestException{
        if (modifiedResource == null){
            throw new BadRequestException("No Input Data");
        }
        Resource selectedRes = getResourceById(resId);
        if(selectedRes != null){
            String resourceName = modifiedResource.getResourceName();
            String visibility = modifiedResource.getResVisibility();
            String resDescription = modifiedResource.getResourceDescription();

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
            selectedRes.setResourceDescription(
                    (resDescription == null || resDescription.isEmpty())?
                            selectedRes.getResourceDescription() :
                            resDescription
            );

            return resourceRepository.updateResource(selectedRes.getResourceId(), selectedRes.getResourceName(), selectedRes.getResourceDescription(), selectedRes.getResVisibility());
        } else {
            throw new BadRequestException("Resource doesn't exist");
        }
    }

    public void deleteResource(@NotNull long resId){
        resourceRepository.deleteById(resId);
    }
    public void deleteAll(){
        resourceRepository.deleteAll();
    }


}
