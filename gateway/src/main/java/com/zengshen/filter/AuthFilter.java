package com.zengshen.filter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

@Component
public class AuthFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        InetSocketAddress remoteAddress = serverHttpRequest.getRemoteAddress();
        String address = remoteAddress.getAddress().getHostAddress();
        String hostName = remoteAddress.getHostName();
        exchange.getRequest().getRemoteAddress().getHostName();
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
        if(address.equals("192.168.31.63")){
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


    @Override
    public int getOrder() {
        return 0;
    }
}
