package com.example.authority;

import com.alibaba.nacos.client.config.utils.MD5;
import com.example.authority.entity.TEcommerceUser;
import com.example.authority.mapper.TEcommerceUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;
import java.util.Date;


@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthorityCenterApplicationTest {

    @Autowired
    private TEcommerceUserMapper tEcommerceUserMapper;

    @Test
    public void test() throws NoSuchAlgorithmException {
        TEcommerceUser tEcommerceUser1 = new TEcommerceUser();
        tEcommerceUser1.setUsername("zengshen.com");
        tEcommerceUser1.setPassword(MD5.getInstance().getMD5String("123456"));
        tEcommerceUser1.setExtraInfo("{}");
        tEcommerceUser1.setCreateTime(new Date());
        tEcommerceUser1.setUpdateTime(new Date());
        tEcommerceUserMapper.insert(tEcommerceUser1);
        TEcommerceUser tEcommerceUser = tEcommerceUserMapper.selectByUsernameAndPassword("dd", "dd");

        System.out.println(tEcommerceUser);
    }

    @Test
    public void rSATest() {


    }
}