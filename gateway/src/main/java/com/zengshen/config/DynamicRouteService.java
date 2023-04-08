package com.zengshen.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

/**
 * 事件推送 aware 动态路由
 */
@Slf4j
@Service
public class DynamicRouteService implements ApplicationEventPublisherAware {

    /**
     * 写路由定义
     */
    private final RouteDefinitionWriter routeDefinitionWriter;

    /**
     * 获取路由定义
     */
    private final RouteDefinitionLocator routeDefinitionLocator;


    /** 事件发布 */
    private ApplicationEventPublisher publisher;

    public DynamicRouteService(RouteDefinitionWriter routeDefinitionWriter, RouteDefinitionLocator routeDefinitionLocator) {
        this.routeDefinitionWriter = routeDefinitionWriter;
        this.routeDefinitionLocator = routeDefinitionLocator;
    }


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
         // 完成事件推送句柄初始化
        this.publisher = applicationEventPublisher;
    }

    public String addRouteDefinition(RouteDefinition definition) {
        log.info("gateway添加路由: {}", definition);

        // 1.保存路由配置并发布
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();

        // 2. 发布事件通知给 gateway, 同步新增的路由定义
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }

    /**
     * 根据路由id删除路由配置
     */
    private String deleteById(String id) {
        log.info("gateway 删除路由定义 id: {}", id);
        try {
            this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
            // 发布事件通知给 gateway 更新路由定义
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
            return "success";
        } catch (Exception e) {
            log.error("gateway 删除路由失败, {}", e.getMessage(), e);
            return "fail";
        }
    }

    /**
     * 更新路由定义
     */

    public String updateByRouteDefinition(RouteDefinition routeDefinition) {
        log.info("gateway 更新路由定义: {}", routeDefinition);
        this.routeDefinitionWriter.delete(Mono.just(routeDefinition.getId()));
        this.routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        return "success";
    }

    public String updateList(List<RouteDefinition> definitionList) {
        log.info("gateway 批量更新路由: {} ", definitionList);
        List<RouteDefinition> routeDefinitions = routeDefinitionLocator.getRouteDefinitions().buffer().blockFirst();
        if (!CollectionUtils.isEmpty(routeDefinitions)) {
            // 清除掉之前的路由定义
            routeDefinitions.forEach(routeDefinition -> this.deleteById(routeDefinition.getId()));
        }
        definitionList.forEach(routeDefinition -> this.updateByRouteDefinition(routeDefinition));
        return "success";
    }


}
