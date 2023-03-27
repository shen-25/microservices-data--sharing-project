package com.zengshen.gateway;

import lombok.Data;

import java.time.LocalTime;

/**
 * @author word
 */
@Data
public class TimeBetweenConfig {
    private LocalTime start;
    private LocalTime end;
}
