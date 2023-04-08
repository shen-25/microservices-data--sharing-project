package com.zengshen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;

/**
 * 配置类,读取 Nacos相关配置，用于配置监听器
 */
@Configuration
public class GatewayConfig {
    /**
     *  读取配置文件的超时时间
     */
    public static final  long DEFAULT_TIMEOUT = 3000;

    /**
     *  nacos 服务地址
     */
    public static  String NACOS_SERVER_ADDR ;

    /**
     * nacos的命名空间
     */
    public static String NACOS_NAMESPACE;

    /**
     * data_id
     */
    public static String NACOS_ROUTE_DATA_ID;
    /**
     * 分组id
     */
    public static String NACOS_ROUTE_GROUP;

    @Value("${spring.cloud.nacos.discovery.server-addr}")
    public void setNacosServerAddr(String nacosServerAddr) {
        NACOS_SERVER_ADDR = nacosServerAddr;
    }

    @Value("${spring.cloud.nacos.discovery.namespace}")
    public void setNacosNamespace(String namespace) {
        NACOS_NAMESPACE = namespace;
    }

    @Value("${nacos.gateway.route.config.data-id}")
    public void setNacosRouteDataId(String nacosRouteDataId) {
        NACOS_ROUTE_DATA_ID = nacosRouteDataId;
    }

    @Value("${nacos.gateway.route.config.group}")
    public void setNacosRouteGroup(String nacosRouteGroup) {
        NACOS_ROUTE_GROUP = nacosRouteGroup;
    }

}
