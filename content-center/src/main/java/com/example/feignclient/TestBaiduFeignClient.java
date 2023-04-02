package com.example.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author word
 */
@FeignClient(name = "baidu", url = "http://www.baidu.com")
public interface TestBaiduFeignClient {

    @GetMapping("")
    String index();
}
