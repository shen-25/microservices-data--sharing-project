package com.example.common.utils;

import com.alibaba.fastjson.JSON;
import com.example.common.constant.CommonConstant;
import com.example.common.vo.LoginUserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Calendar;

/**
 * jwt token 解析工具
 * @author word
 */
public class TokenParseUtil {

    /**
     * 从token中解析出 LoginUserInfo 对象
     */
    public static LoginUserInfo parseUserInfoFormToken(String token) throws Exception {
        if (token == null) {
            return null;
        }

        Jws<Claims> claimsJws = parseToken(token, getPublicKey());
        Claims body = claimsJws.getBody();
        if (body.getExpiration().before(Calendar.getInstance().getTime())) {
            return null;
        }
        // 返回 Token 中保存的用户信息
        return JSON.parseObject(
                body.get(CommonConstant.JWT_USER_INFO_KEY).toString(),
                LoginUserInfo.class
        );
    }

    private static Jws<Claims> parseToken(String token, PublicKey publicKey) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build().parseClaimsJws(token);
    }

    /**
     * 根据本地存储的公钥获取到 PublicKey 对象
     */
    private static PublicKey getPublicKey() throws Exception {

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
                Base64.getDecoder().decode(CommonConstant.PUBLIC_KEY)
        );
        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }
}
