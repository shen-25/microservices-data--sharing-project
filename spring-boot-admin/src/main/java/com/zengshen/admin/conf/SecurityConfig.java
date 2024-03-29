package com.zengshen.admin.conf;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import io.netty.handler.codec.http.HttpMethod;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;

/**
 * 配置安全认证,以便其他的微服务可以注册
 * 文档 https://codecentric.github.io/spring-boot-admin/2.2.1/#_securing_spring_boot_admin_server
 * @author word
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
//        successHandler.setTargetUrlParameter("redirectTo");
//        successHandler.setDefaultTargetUrl("/");
//
//        http.authorizeRequests()
//                // 配置公开访问的url
//                .antMatchers("/assets/**").permitAll()
//                .antMatchers("/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().loginPage("/login").successHandler(successHandler)
//                .and()
//                .logout().logoutUrl("/logout")
//                .and()
//                .httpBasic()
//                .and()
//
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                 //忽略这些路径的csrf保护以便admin-client注册
//                .ignoringAntMatchers("/instances", "/actuator/**", "/instances/*");
//
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        SavedRequestAwareAuthenticationSuccessHandler successHandler =
                new SavedRequestAwareAuthenticationSuccessHandler();

        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl("/");

        http.authorizeRequests()
                // 1. 配置所有的静态资源和登录页可以公开访问
                .antMatchers( "/assets/**").permitAll()
                .antMatchers( "/login").permitAll()
                // 2. 其他请求, 必须要经过认证
                .anyRequest().authenticated()
                .and()
                // 3. 配置登录和登出路径
                .formLogin().loginPage( "/login")
                .successHandler(successHandler)
                .and()
                .logout().logoutUrl("/logout")
                .and()
                // 4. 开启 http basic 支持, 其他的服务模块注册时需要使用
                .httpBasic()
                .and()
                // 5. 开启基于 cookie 的 csrf 保护
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                // 6. 忽略这些路径的 csrf 保护以便其他的模块可以实现注册
                .ignoringAntMatchers(
                       "/instances",
                      "/actuator/**"
                );
    }
}
