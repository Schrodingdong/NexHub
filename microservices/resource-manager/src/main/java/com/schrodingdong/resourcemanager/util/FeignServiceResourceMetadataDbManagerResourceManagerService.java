package com.schrodingdong.resourcemanager.util;

import com.schrodingdong.resourcemanager.model.ResourceModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//@FeignClient(value = "resourceMetadataDbManager", url = "${metadata-db.url}/res")
@FeignClient(name = "${metadata-db.name}")
public interface FeignServiceResourceMetadataDbManagerResourceManagerService {
    @PostMapping(value = "/res/add/{userId}", consumes = "application/json")
    ResourceModel addResourceToUser(@PathVariable Long userId, ResourceModel resourceToSend);
}
