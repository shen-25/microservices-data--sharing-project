package com.zengshen.gateway;

import java.time.LocalTime;

/**
 * @author word
 */
public class TimeBetweenConfig {
    private LocalTime start;
    private LocalTime end;

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }
}
