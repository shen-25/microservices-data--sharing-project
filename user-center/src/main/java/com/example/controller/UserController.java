package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
