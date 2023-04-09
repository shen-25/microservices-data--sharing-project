package com.example.service;

import com.example.mapper.UserMapper;
import com.example.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User findById(Integer id) {
        log.info("我被请求了，我的地址");
        return userMapper.selectByPrimaryKey(id);
    }
}
