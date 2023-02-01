package com.example.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;
    private String wxId;
    private String wxNickname;
    private String roles;
    private String avatarUrl;
    private Date createTime;
    private Date updateTime;
    private Integer bonus;
}

