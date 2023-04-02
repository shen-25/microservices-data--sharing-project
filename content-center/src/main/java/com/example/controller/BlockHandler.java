package com.example.controller;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BlockHandler {

    public static String block(String a, BlockException e) {
        log.warn("限流或者被降级了", e);
        return "被限流或被降级";
    }
}
