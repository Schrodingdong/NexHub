package com.nexhub.databasemanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexhub.databasemanager.model.Resource;
import com.nexhub.databasemanager.service.ResourceService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/res")
@AllArgsConstructor
public class ResourceController implements IResourceController {
    private final ResourceService resourceService;
    private final ObjectMapper objectMapper;


    @Override
    @PostMapping("/add/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> addResourceForUser(@RequestBody @Valid Resource resource, @PathVariable long userId) {
        Resource resource1 = resourceService.saveResourceForUser(resource, userId);
        return ResponseEntity.ok().body(
                objectMapper.createObjectNode()
                        .put("resourceId",resource1.getResourceId())
                        .put("resourceName",resource1.getResourceName())
                        .put("resourceDescription",resource1.getResourceDescription())
                        .put("resourceBucketId",resource1.getResourceBucketId())
                        .put("resourceHolderId",resource1.getResourceHolderId())
                        .put("resourceVisibility",resource1.getResourceVisibility())
        );
    }

    @Override
    @GetMapping("/get/{resId}")
    public ResponseEntity<?> getResource(@PathVariable @NotNull long resId) {
        Resource resource1 = resourceService.getResourceById(resId);
        return ResponseEntity.ok().body(
                objectMapper.createObjectNode()
                        .put("resourceId",resource1.getResourceId())
                        .put("resourceName",resource1.getResourceName())
                        .put("resourceDescription",resource1.getResourceDescription())
                        .put("resourceBucketId",resource1.getResourceBucketId())
                        .put("resourceHolderId",resource1.getResourceHolderId())
                        .put("resourceVisibility",resource1.getResourceVisibility())
        );
    }

    @Override
    @GetMapping("/get/all")
    public ResponseEntity<?> getAllResources() throws JsonProcessingException {
        List<Resource> allResources = resourceService.getAllResources();
        return ResponseEntity.ok().body(
                allResources
        );
    }

    @Override
    @GetMapping("/get/from-res-name/{name}")
    public ResponseEntity<?> getAllResourcesOfName(@PathVariable @NotNull String name) throws JsonProcessingException {
        List<Resource> resourcesByName = resourceService.getResourcesByName(name);
        return ResponseEntity.ok().body(
                resourcesByName
        );
    }

    @Override
    @GetMapping("/get/all-from-user/{userId}")
    public ResponseEntity<?> getALlResourcesFromUser(@PathVariable @NotNull long userId) throws JsonProcessingException {
        List<Resource> allResourcesFromUser = resourceService.getAllResourcesFromUser(userId);
        return ResponseEntity.ok().body(
                allResourcesFromUser
        );
    }

    @Override
    @GetMapping("/get/public-from-user/{userId}")
    public ResponseEntity<?> getAllPublicResourcesFromUser(@PathVariable @NotNull long userId) throws JsonProcessingException {
        List<Resource> allPublicResourcesFromUser = resourceService.getAllPublicResourcesFromUser(userId);
        return ResponseEntity.ok().body(
                allPublicResourcesFromUser
        );
    }

    @Override
    @PutMapping("/update/{resId}")
    public ResponseEntity<?> updateResourceForUser(@PathVariable long resId, @RequestBody Resource resource) {
        if (resource.getResourceBucketId() != null){
            return ResponseEntity.badRequest().body(
                    objectMapper.createObjectNode()
                            .put("message","Resource bucketId cannot be updated")
            );
        }
        Resource resource1 = resourceService.updateResource(resId, resource);
        return ResponseEntity.ok().body(
                objectMapper.createObjectNode()
                        .put("resourceId",resource1.getResourceId())
                        .put("resourceName",resource1.getResourceName())
                        .put("resourceDescription",resource1.getResourceDescription())
                        .put("resourceBucketId",resource1.getResourceBucketId())
                        .put("resourceHolderId",resource1.getResourceHolderId())
                        .put("resourceVisibility",resource1.getResourceVisibility())
        );
    }

    @Override
    @DeleteMapping("/delete/{resId}")
    public ResponseEntity<?> deleteResource(@PathVariable long resId) {
        resourceService.deleteResource(resId);
        return ResponseEntity.accepted().body(
                objectMapper.createObjectNode()
                        .put("message","Resource deleted successfully")
        );
    }

    @Override
    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAll(){
        resourceService.deleteAll();
        return ResponseEntity.accepted().body(
                objectMapper.createObjectNode()
                        .put("message","All resources deleted successfully")
        );
    }


    @Override
    @DeleteMapping("/delete/all-of-user/{userId}")
    public ResponseEntity<?> deleteAllResourcesOdUser(@PathVariable long userId) {
        resourceService.deleteResourcesOfUser(userId);
        return ResponseEntity.accepted().body(
                objectMapper.createObjectNode()
                        .put("message","Resource deleted successfully")
        );
    }

}
