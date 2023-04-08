package com.example.authority.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * t_ecommerce_user
 * @author 
 */
@Data
public class TEcommerceUser implements Serializable {
    /**
     * 自增主键
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * MD5 加密之后的密码
     */
    private String password;

    /**
     * 额外的信息
     */
    private String extraInfo;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}