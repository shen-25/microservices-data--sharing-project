package com.example.controller;


import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.example.domain.dto.user.UserDTO;
import com.example.feignclient.TestBaiduFeignClient;
import com.example.feignclient.TestUserCenterFeignClient;
import com.example.service.content.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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

    @Autowired
    private TestService testService;

    @GetMapping("/test-a")
    public Object testA() {
        testService.common();
        return "test-a";
    }

    @GetMapping("/test-b")
    public Object testB() {
        testService.common();
        return "test-b" ;
    }

    @GetMapping("/test-hot")
    @SentinelResource("hot")
    public Object testHot(@RequestParam(required = false) String a,
                           @RequestParam(required = false) String b) {

        return a + " " + b;
    }

    @GetMapping("/test-c")
    public Object testC() {
        initFlowQpsRule();
        return "success";
    }

    private  void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule("/test-hot");
        // set limit qps to 20
        rule.setCount(20);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

}
