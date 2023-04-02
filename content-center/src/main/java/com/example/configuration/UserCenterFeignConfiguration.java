package com.example.configuration;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * 该Feign Client的配置类，注意：
 * 1. 该类可以独立出去；
 * 2. 该类上也可添加@Configuration声明是一个配置类；
 * 配置类上也可添加@Configuration注解，声明这是一个配置类；
 * 但此时千万别将该放置在主应用程序上下文@ComponentScan所扫描的包中，
 * 否则，该配置将会被所有Feign Client共享，无法实现细粒度配置！
 */
public class UserCenterFeignConfiguration {

    @Bean
    public Logger.Level level() {
        // feign打印所有请求
        return Logger.Level.FULL;
    }
}
