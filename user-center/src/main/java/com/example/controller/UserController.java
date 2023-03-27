package com.example.controller;

import com.example.dao.user.UserMapper;
import com.example.domain.entity.user.User;
import com.example.service.user.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author word
 */

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    @ApiOperation(value = "根据用户id")
    public User findById(@PathVariable Integer id,HttpServletRequest request) {
        System.out.println(request);

        return userService.findById(id);
    }

}
