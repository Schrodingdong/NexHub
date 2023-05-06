package com.example.authentification.util;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

//@FeignClient(value = "bucketManager", url = "${resource-manager-db.url}/buckets")
@FeignClient(name = "${resource-manager-db.name}")
public interface FeignClientBucketManager {
    @PostMapping(value = "/buckets/create/{bucketId}")
    ResponseEntity<?> createNewBucket(@PathVariable String bucketId);
}
