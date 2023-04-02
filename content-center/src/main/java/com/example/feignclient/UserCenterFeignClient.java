package com.example.feignclient;

import com.example.domain.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author word
 */
// configuration = UserCenterFeignConfiguration.class 配置日志输出级别
@FeignClient(name = "user-center")
public interface UserCenterFeignClient {

    @GetMapping("/users/{id}")
    UserDTO findById(@PathVariable Integer id);
}
