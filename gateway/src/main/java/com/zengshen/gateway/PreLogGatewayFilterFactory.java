package com.zengshen.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author word
 */
@Slf4j
@Component
public class PreLogGatewayFilterFactory  extends AbstractNameValueGatewayFilterFactory {
    @Override
    public GatewayFilter apply(NameValueConfig config) {
        return (exchange, chain) -> {
            log.info("请求进来了, {}, {}", config.getName(), config.getValue());

            ServerHttpRequest modified = exchange.getRequest()
                    .mutate().build();
            ServerWebExchange buildExchange = exchange.mutate().request(modified).build();
            return chain.filter(buildExchange);
        };
    }
}
