package com.nexhub.databasemanager.controller;

import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.service.ResourceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/res")
@CrossOrigin
public class ResourceController implements IResourceController {
    @Autowired
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }


    @Override
    @PostMapping("/add/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Resource addResourceToUser(@RequestBody @Valid Resource resource, @PathVariable @NotNull long userId) {
        return resourceService.saveResource(userId, resource);
    }

    @Override
    @GetMapping("/get/{resId}")
    @ResponseStatus(HttpStatus.OK)
    public Resource getResource(@PathVariable @NotNull long resId) {
        return resourceService.getResourceById(resId);
    }

    @Override
    public List<Resource> getAllResources() {
        return resourceService.getAllResources();
    }

    @Override
    @GetMapping("/get/from-res-name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Resource> getAllResourcesOfName(@PathVariable @NotNull String name) {
        return resourceService.getResourcesByName(name);
    }

    @Override
    @PutMapping("/update/from-user/{userId}/resource-id/{resId}")
    @ResponseStatus(HttpStatus.OK)
    public Resource updateResourceForUser(@PathVariable long resId,@RequestBody @Valid Resource resource, @PathVariable @NotNull long userId) {
        return resourceService.updateResource(resId, resource, userId);
    }

    @Override
    @DeleteMapping("/delete/{resId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteResourceFromUser(long resId) {
        resourceService.deleteResource(resId);
    }

    @Override
    @DeleteMapping("/delete/all")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAll(){
        resourceService.deleteAll();
    }


}
