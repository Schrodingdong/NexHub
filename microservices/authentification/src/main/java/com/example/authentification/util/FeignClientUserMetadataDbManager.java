package com.example.authentification.util;

import com.example.authentification.appuser.MetadataUserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

//@FeignClient(value = "userMetadataDbManager", url = "${metadata-db.url}/users")
@FeignClient(name = "${metadata-db.name}")
public interface FeignClientUserMetadataDbManager {
    @PostMapping(value = "/users/add", consumes = "application/json")
    MetadataUserModel addNewUser(MetadataUserModel userMetadata);
}
