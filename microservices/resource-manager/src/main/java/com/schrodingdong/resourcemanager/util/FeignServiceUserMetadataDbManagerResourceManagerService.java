package com.schrodingdong.resourcemanager.util;

import com.schrodingdong.resourcemanager.model.UserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "userMetadataDbManager", url = "${metadata-db.url}")
//@FeignClient(name = "${metadata-db.name}")
public interface FeignServiceUserMetadataDbManagerResourceManagerService {
    @GetMapping("/users/get/mail/{mail}")
    UserModel getUserByMail(@PathVariable String mail);
}
