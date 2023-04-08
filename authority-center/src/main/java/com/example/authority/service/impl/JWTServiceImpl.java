package com.example.authority.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.authority.constant.AuthorityConstant;
import com.example.authority.entity.TEcommerceUser;
import com.example.authority.mapper.TEcommerceUserMapper;
import com.example.authority.service.JWTService;
import com.example.common.vo.LoginUserInfo;
import com.example.common.vo.UsernameAndPassword;
import com.example.common.constant.CommonConstant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class JWTServiceImpl implements JWTService {

    @Autowired
    private TEcommerceUserMapper tEcommerceUserMapper;

    @Override
    public String generateToken(String username, String password) throws Exception {
        return generateToken(username, password, 0);
    }

    @Override
    public String generateToken(String username, String password, int expire) throws Exception {
        // 验证用户
        TEcommerceUser ecommerceUser = tEcommerceUserMapper.selectByUsernameAndPassword(username, password);
        if (ecommerceUser == null) {
            log.info("用户不存在或者密码错误");
            return null;
        }
        LoginUserInfo loginUserInfo = new LoginUserInfo(ecommerceUser.getId(), ecommerceUser.getUsername());
        if (expire <= 0) {
            expire = AuthorityConstant.DEFAULT_EXPIRE_DAY;
        }
        // 当前时间加上 expire days
        ZonedDateTime zdt = LocalDate.now().plus(expire, ChronoUnit.DAYS)
                .atStartOfDay(ZoneId.systemDefault());
        Date expireDate = Date.from(zdt.toInstant());
        return Jwts.builder()
                .claim(CommonConstant.JWT_USER_INFO_KEY, JSON.toJSONString(loginUserInfo))
                .setId(UUID.randomUUID().toString())
                .setExpiration(expireDate)
                .signWith(getPrivateKey(), SignatureAlgorithm.RS256).compact();
    }

    @Override
    public String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword) throws Exception {
        TEcommerceUser oldUser = tEcommerceUserMapper.selectByUsername(usernameAndPassword.getUsername());
        if (oldUser != null) {
            log.error("用户名已存在, 用户名为: {}", oldUser.getUsername());
            return null;
        }
        TEcommerceUser ecommerceUser = new TEcommerceUser();
        ecommerceUser.setUsername(usernameAndPassword.getUsername());
        // 加密后的密码
        ecommerceUser.setPassword(usernameAndPassword.getPassword());
        ecommerceUser.setExtraInfo("{}");
        ecommerceUser.setUpdateTime(new Date());
        ecommerceUser.setCreateTime(new Date());

        // 注册用户，插入数据库中
        tEcommerceUserMapper.insert(ecommerceUser);
        log.info("注册用户成功, 用户名为: {}", ecommerceUser.getUsername());

        return generateToken(ecommerceUser.getUsername(), ecommerceUser.getPassword());
    }
    /**
     * <h2>根据本地存储的私钥获取到 PrivateKey 对象</h2>
     * */
    private PrivateKey getPrivateKey() throws Exception {

        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
                Base64.getDecoder().decode(AuthorityConstant.PRIVATE_KEY));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }
}
