package com.example.feignclient;

// 通过 @FeifnClient 指定目标服务名称

import com.example.domain.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "user-center")
public interface LearnFeignClient {
    @PostMapping("/feign/{id}")
    public User queryUser(@PathVariable String id, @RequestBody User user);
}
