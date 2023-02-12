package com.example.feignclient;

import com.example.domain.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author word
 */
// configuration = UserCenterFeignConfiguration.class 配置日志输出级别
@FeignClient(name = "user-center")
public interface TestUserCenterFeignClient {

    @GetMapping("/q")
    UserDTO query(@SpringQueryMap  UserDTO userDTO);
}
