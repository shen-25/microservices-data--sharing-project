package com.example.service;

import brave.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SleuthTraceInfoService {

    @Autowired
    private Tracer tracer;

    public void userCurrentTraceInfo() {
        log.info("Sleuth traceId: {}", tracer.currentSpan().context().traceId());
        log.info("Sleuth spanId: {}", tracer.currentSpan().context().spanId());

    }

}
