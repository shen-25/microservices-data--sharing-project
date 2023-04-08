package com.zengshen.gateway;

import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.text.DateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author word
 */
//@Component
public class TimeBetweenRoutePredicateFactory extends AbstractRoutePredicateFactory<TimeBetweenConfig> {

    public TimeBetweenRoutePredicateFactory() {
        super(TimeBetweenConfig.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(TimeBetweenConfig config) {
        LocalTime start = config.getStart();
        LocalTime end = config.getEnd();
        return serverWebExchange -> {
            LocalTime now = LocalTime.now();
//            return now.isAfter(start) && now.isBefore(end);
            // 任何时候开发，避免
            return true;
        };


    }

    @Override
    public List<String> shortcutFieldOrder() {
        // 构造参数， 映射
        return Arrays.asList("start", "end");
    }

    public static void main(String[] args) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        System.out.println(dateTimeFormatter.format(LocalTime.now()));

    }
}
