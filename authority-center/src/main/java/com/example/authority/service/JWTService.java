package com.example.authority.service;

import com.example.common.vo.UsernameAndPassword;

public interface JWTService {
    /**
     * 生成token,默认超时时间
     */
    String generateToken(String username, String password)  throws Exception;

   String generateToken(String username, String password, int expire)  throws Exception;

    /**
     * 注册后, 生成token并返回
     */
   String registerUserAndGenerateToken(UsernameAndPassword usernameAndPassword)  throws Exception;


}
