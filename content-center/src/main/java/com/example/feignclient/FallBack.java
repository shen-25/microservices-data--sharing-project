package com.example.feignclient;

import com.example.domain.dto.user.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class FallBack implements UserCenterFeignClient {

    @Override
    public UserDTO findById(Integer id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setWxNickname("feign FallBack");
        return userDTO;
    }
}
