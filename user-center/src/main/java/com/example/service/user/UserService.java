package com.example.service.user;

import com.example.dao.user.UserMapper;
import com.example.domain.entity.user.User;
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
