package com.nexhub.databasemanager.controller;

import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.service.ResourceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/res")
public class ResourceController implements IResourceController {
    @Autowired
    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }


    @Override
    @PostMapping("/add/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public Resource addResourceForUser(@RequestBody @Valid Resource resource,@PathVariable long userId) {
        return resourceService.saveResourceForUser(resource,userId);
    }

    @Override
    @GetMapping("/get/{resId}")
    @ResponseStatus(HttpStatus.OK)
    public Resource getResource(@PathVariable @NotNull long resId) {
        return resourceService.getResourceById(resId);
    }

    @Override
    @GetMapping("/get/all")
    @ResponseStatus(HttpStatus.OK)
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
    @GetMapping("/get/all-from-user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Resource> getALlResourcesFromUser(@PathVariable @NotNull long userId) {
        return resourceService.getAllResourcesFromUser(userId);
    }

    @Override
    @GetMapping("/get/public-from-user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Resource> getAllPublicResourcesFromUser(@PathVariable @NotNull long userId) {
        return resourceService.getAllPublicResourcesFromUser(userId);
    }

    @Override
    @PutMapping("/update/{resId}")
    @ResponseStatus(HttpStatus.OK)
    public Resource updateResourceForUser(@PathVariable long resId,@RequestBody @Valid Resource resource) {
        return resourceService.updateResource(resId, resource);
    }

    @Override
    @DeleteMapping("/delete/{resId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteResourceFromUser(@PathVariable long resId) {
        resourceService.deleteResource(resId);
    }

    @Override
    @DeleteMapping("/delete/all")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAll(){
        resourceService.deleteAll();
    }


}
