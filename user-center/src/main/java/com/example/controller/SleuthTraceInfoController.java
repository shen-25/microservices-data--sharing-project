package com.example.controller;

import com.example.service.SleuthTraceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class SleuthTraceInfoController {

    @Autowired
    private SleuthTraceInfoService sleuthTraceInfoService;

    @GetMapping("/sleuth")
    public void logSleuthInfo() {
        sleuthTraceInfoService.userCurrentTraceInfo();
    }
}
