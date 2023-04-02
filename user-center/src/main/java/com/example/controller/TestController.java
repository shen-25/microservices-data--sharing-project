package com.example.controller;

import com.example.dao.user.UserMapper;
import com.example.domain.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return user;
    }

    @GetMapping("q")
    public Object q(User user) {
        return user;
    }


    @PostMapping("feign/{id}")
    public Object feign(@PathVariable String id, @RequestBody  User user) {
        if (user == null) {
            return "error";
        }
        user.setId(Integer.valueOf(id));
        user.setUpdateTime(new Date());
        user.setWxNickname("调用feign成功");
        return user;
    }
}
