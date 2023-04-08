package com.zengshen.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * 通过 nacos 下发动态路由配置, 监听 Nacos 中的路由配置变更
 * @author word
 */
@Slf4j
@Component
@DependsOn({"gatewayConfig"})
public class NacosDynamicRouteService {

    @Autowired
    private DynamicRouteService dynamicRouteService;

    private ConfigService configService;


    /**
     * 在 Bean 容器中构造完成之后执行 init 方法
     */
    @PostConstruct
    public void init(){
        log.info("gateway route init...");
        configService = initConfigService();
        if (configService == null) {
            log.error("初始化 configService 失败");
            return;
        }
        // 通过 nacos Config 指定路由配置路径去获取路由配置
        try {
            String config = configService.getConfig(GatewayConfig.NACOS_ROUTE_DATA_ID,
                    GatewayConfig.NACOS_ROUTE_GROUP, GatewayConfig.DEFAULT_TIMEOUT);

            log.info("获取 当前的 gateway 路由配置: {}", config);
            List<RouteDefinition> definitionList = JSON.parseArray(config, RouteDefinition.class);
            if (!CollectionUtils.isEmpty(definitionList)) {
                definitionList.forEach(routeDefinition -> dynamicRouteService.addRouteDefinition(routeDefinition));
            }

        } catch (NacosException e) {
            log.info("初始化路由失败, {}", e.getMessage(), e);
        }
        // 设置监听器
        dynamicRouteByNacosListener(GatewayConfig.NACOS_ROUTE_DATA_ID,
                GatewayConfig.NACOS_ROUTE_GROUP);
    }


    private ConfigService initConfigService() {
        Properties properties = new Properties();
        properties.setProperty("serverAddr", GatewayConfig.NACOS_SERVER_ADDR);
        properties.setProperty("namespace", GatewayConfig.NACOS_NAMESPACE);
        try {
            configService = NacosFactory.createConfigService(properties);
            return configService;
        } catch (NacosException e) {
            log.error("初始化 gateway nacos config error: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 监听 Nacos 下发的动态路由配置
     */
    private void dynamicRouteByNacosListener(String dataId, String group) {
        // 给 nacos conf 客户端添加一个监听器
        try {
            configService.addListener(dataId, group, new Listener() {
                // 自己提供的线程池执行操作， 默认就可
                @Override
                public Executor getExecutor() {
                    return null;
                }
                // 监听器收到的配置更新
                @Override
                public void receiveConfigInfo(String s) {
                    log.info("监听器收到的路由配置是: {}", s);
                    List<RouteDefinition> definitionList = JSON.parseArray(s, RouteDefinition.class);
                    dynamicRouteService.updateList(definitionList);
                }
            });
        } catch (NacosException e) {
            log.error("监听器添加失败, 配置出错, {}", e.getMessage(), e);
        }
    }
}
