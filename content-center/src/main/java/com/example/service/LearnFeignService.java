package com.example.service;

import com.example.domain.entity.User;
import com.example.feignclient.LearnFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LearnFeignService {

    @Autowired
    private LearnFeignClient learnFeignClient;

    public Object queryById(String id) {
        User user = new User();
        user.setWxNickname("你有没有调用成功啊");
        User user1 = learnFeignClient.queryUser(id, user);
        return user1;
    }
}
