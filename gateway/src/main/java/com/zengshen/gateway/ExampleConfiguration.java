package com.zengshen.gateway;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * 全局过滤器
 * 我实现一个自定义的ip不能访问，
 * 结果返回
 * 另一种实现在 AuthorizeFilter里
 */
//@Configuration
//@Slf4j
public class ExampleConfiguration {

    @Bean(name = "test")
    @Order(1)
    public GlobalFilter test() {
        return new GlobalFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                ServerHttpRequest serverHttpRequest = exchange.getRequest();
                InetSocketAddress remoteAddress = serverHttpRequest.getRemoteAddress();
                String address = remoteAddress.getAddress().getHostAddress();
                String hostName = remoteAddress.getHostName();
                if (address.equals("127.0.0.1")) {
                    ServerHttpResponse response = exchange.getResponse();
                    JSONObject message = new JSONObject();
                    message.put("status", -1);
                    message.put("data", "鉴权失败");
                    byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
                    DataBuffer buffer = response.bufferFactory().wrap(bits);
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    //指定编码，否则在浏览器中会中文乱码
                    response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
                    return response.writeWith(Mono.just(buffer));
                }
                if(address.equals("192.168.93.1")){
                    //重定向(redirect)到登录页面
                    String url = "http://www.baidu.com";
                    ServerHttpResponse response = exchange.getResponse();
                    //303状态码表示由于请求对应的资源存在着另一个URI，应使用GET方法定向获取请求的资源
                    response.setStatusCode(HttpStatus.SEE_OTHER);
                    response.getHeaders().set(HttpHeaders.LOCATION, url);
                    return response.setComplete();
                }
                return chain.filter(exchange);
            }
        };
    }
}
