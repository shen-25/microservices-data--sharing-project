package com.example.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * @author word
 */
public class UserCenterFeignConfiguration {

    @Bean
    public Logger.Level level() {
        // feign打印所有请求
        return Logger.Level.FULL;
    }
}
