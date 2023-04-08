package com.example.authority.controller;

import com.alibaba.fastjson.JSON;
import com.example.authority.service.JWTService;
import com.example.common.vo.JwtToken;
import com.example.common.vo.UsernameAndPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 对外暴露的接口, 其他微服务调用
 */
@Slf4j
@RestController
@RequestMapping("/authority")
public class AuthorityController {

    @Autowired
    private JWTService jwtService;

    /**
     * 授权中心获取token, 没有统一的异常处理
     *
     * @param usernameAndPassword
     * @return
     */
    @PostMapping("/token")
    public JwtToken token(@RequestBody UsernameAndPassword usernameAndPassword) throws Exception {
        log.info("用户信息: {}", JSON.toJSONString(usernameAndPassword));
        return new JwtToken(jwtService.generateToken(usernameAndPassword.getUsername(),
                usernameAndPassword.getPassword()));
    }

    @PostMapping("/register")
    public JwtToken register(@RequestBody UsernameAndPassword usernameAndPassword) throws Exception {
        log.info("用户进行注册, 用户信息: {}", JSON.toJSONString(usernameAndPassword));
        return new JwtToken(jwtService.registerUserAndGenerateToken(usernameAndPassword));

    }

    @GetMapping("/test")
    public String test() {
        return "我是authority";
    }
}
