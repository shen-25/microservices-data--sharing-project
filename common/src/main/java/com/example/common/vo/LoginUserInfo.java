package com.example.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author word
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserInfo implements Serializable {
    private Long id;
    private String username;
}
