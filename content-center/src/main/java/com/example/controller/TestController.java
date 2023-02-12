package com.example.controller;


import com.example.domain.dto.user.UserDTO;
import com.example.feignclient.TestBaiduFeignClient;
import com.example.feignclient.TestUserCenterFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/test")
    public Object test() {
        return "hello world";
    }

    @GetMapping("/test2")
    public Object test2() {
        List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
        return instances;
    }


    @Autowired
    private TestUserCenterFeignClient testUserCenterFeignClient;

    @GetMapping("/test-get")
    public Object testGet(UserDTO userDTO) {
        UserDTO query = testUserCenterFeignClient.query(userDTO);
        return query;
    }

    @Autowired
    private TestBaiduFeignClient testBaiduFeignClient;

    @GetMapping("/baidu")
    public Object baidu() {

        String index = testBaiduFeignClient.index();
        return index;

    }
}
