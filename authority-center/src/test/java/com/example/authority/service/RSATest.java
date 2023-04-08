package com.example.authority.service;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

/**
 * <h1>RSA 非对称加密算法: 生成公钥和私钥</h1>
 * */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class RSATest {

    @Test
    public void generateKeyBytes() throws Exception {

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);

        // 生成公钥和私钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 获取公钥和私钥对象
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        log.info("private key: [{}]", Base64.getEncoder().encodeToString(privateKey.getEncoded()));
        log.info("public key: [{}]", Base64.getEncoder().encodeToString(publicKey.getEncoded()));
    }
}
