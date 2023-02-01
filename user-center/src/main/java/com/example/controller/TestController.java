package com.example.controller;

import com.example.dao.user.UserMapper;
import com.example.domain.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author word
 */

@RestController
public class TestController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("test")
    public Object test() {
        User user = new User();
//        user.setId(1);
        user.setWxNickname("xxx");
        user.setAvatarUrl("xxx");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        this.userMapper.insert(user);
        return  user;
    }

}
