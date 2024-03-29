package com.example.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author word
 */
@Slf4j
@Service
public class TestService {
    @SentinelResource("common")
    public String common() {
        return "common";
    }
}
