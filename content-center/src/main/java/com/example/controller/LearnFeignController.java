package com.example.controller;

import com.example.service.LearnFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LearnFeignController {


    @Autowired
    private LearnFeignService learnFeignService;

    @GetMapping("/test")
    public Object testFeign() {
        return learnFeignService.queryById("11111");
    }
}
