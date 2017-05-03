package ru.sbt.ds;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("photo-service")
public interface PhotoServiceClient {
    @RequestMapping("/img")
    byte[] getPhoto(@RequestParam("id") int id);
}