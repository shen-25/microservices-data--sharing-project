package com.example.authority.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.authority.service.JWTService;
import com.example.common.utils.TokenParseUtil;
import com.example.common.vo.LoginUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class JWTServiceTest {

    @Autowired
    private JWTService jwtService;

    @Test
    public void test() throws Exception {
        String token = jwtService.generateToken("aaa", "e10adc3949ba59abbe56e057f20f883e");

        log.info("生成的token: {}", token);

        LoginUserInfo loginUserInfo = TokenParseUtil.parseUserInfoFormToken(token);

        log.info("解析出的token用户信息： {}", JSON.toJSONString(loginUserInfo));
    }
}
