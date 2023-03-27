package com.example.controller;


import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.example.controller.content.BlockHandler;
import com.example.domain.dto.user.UserDTO;
import com.example.feignclient.TestBaiduFeignClient;
import com.example.feignclient.TestUserCenterFeignClient;
import com.example.service.content.TestService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author word
 */
@RestController
@Slf4j
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

    @GetMapping("test-sentinel-api")
    public String testSentinelApi(@RequestParam(required = false) String a)  {
        String resourceName = "test-sentinel-api";
        // 定义来源
        ContextUtil.enter(resourceName, "test-wfw");
        // 定义一个sentinel保护的资源
        try (Entry entry = SphU.entry("test-sentinel-api")) {
            // 被保护的业务逻辑
            if (StringUtils.isBlank(a)) {
                throw new IllegalArgumentException("a不能为空");
            }
            return a;
        }  catch (BlockException blockException) {
            // 资源访问阻止，被限流或被降级
            // 在此处进行相应的处理操作
            log.warn("限流或者被降级了");
            return "被限流或被降级";
        } catch (IllegalArgumentException illegalArgumentException) {
            Tracer.trace(illegalArgumentException);
            return "参数非法";
        } finally {
            ContextUtil.exit();
        }
    }

    /**
     * 指定block调用的方法和调用方法的类
     * @param a
     * @return
     */
    @GetMapping("test-sentinel")
    @SentinelResource(value = "test-sentinel",
            blockHandler = "block",
            blockHandlerClass = BlockHandler.class,
    fallback = "fallback")
    public String testSentinel(@RequestParam(required = false) String a)  {
        if (StringUtils.isBlank(a)) {
            throw new IllegalArgumentException("a不能为空");
        }
        return a;
    }

//    public String block(String a, BlockException e) {
//        log.warn("限流或者被降级了", e);
//        return "被限流或被降级";
//    }
    public String fallback(String a, BlockException e) {
        log.warn("限流或者被降级了 fallback", e);
        return "被限流或被降级 fallback";
    }

    private void initFlowQpsRule() {

        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule("/test-hot");
        // set limit qps to 20
        rule.setCount(20);
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setLimitApp("default");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/test-rest-template")
    public UserDTO test(@RequestParam Integer userId){
        UserDTO forObject = this.restTemplate.
                getForObject("http://user-center/users/{userId}", UserDTO.class, userId);
        return forObject;
    }

}
