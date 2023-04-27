package com.schrodingdong.resourcemanager.util;

import com.schrodingdong.resourcemanager.model.ResourceModel;
import com.schrodingdong.resourcemanager.service.ResourceService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "resourceMetadataDbManager", url = "${metadata-db.url}/res")
public interface FeignServiceResourceMetadataDbManager {

    @PostMapping(value = "/add/{userId}", consumes = "application/json")
    ResourceModel addResourceToUser(@PathVariable Long userId, ResourceModel resourceToSend);
}
