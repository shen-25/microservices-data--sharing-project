package com.example.configuration;
//
//import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
//import com.alibaba.cloud.nacos.ribbon.NacosServer;
//import com.alibaba.nacos.api.exception.NacosException;
//import com.alibaba.nacos.api.naming.NamingService;
//import com.alibaba.nacos.api.naming.pojo.Instance;
//import com.netflix.client.config.IClientConfig;
//import com.netflix.loadbalancer.AbstractLoadBalancerRule;
//import com.netflix.loadbalancer.BaseLoadBalancer;
//import com.netflix.loadbalancer.ILoadBalancer;
//import com.netflix.loadbalancer.Server;
//import lombok.SneakyThrows;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @author word
// */
//
//@Slf4j
//@Configuration
//public class NacosWeightedRule extends AbstractLoadBalancerRule {
//
//    @Autowired
//    private NacosDiscoveryProperties nacosDiscoveryProperties;
//
//    @Override
//    public void initWithNiwsConfig(IClientConfig iClientConfig) {
//        //读取配置文件，初始化
//    }
//
//    @Override
//    public Server choose(Object o) {
//        BaseLoadBalancer loadBalancer = (BaseLoadBalancer)this.getLoadBalancer();
//        // 请求微服务的名称
//        String name = loadBalancer.getName();
//        // 实现负载均衡算法
//
//        // 拿到服务发现的api
//        NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
//        try {
//            Instance instance = namingService.selectOneHealthyInstance(name);
//            log.info("选择的实例是: port = {}, instance = {}", instance.getPort(), instance);
//            return new NacosServer(instance);
//        } catch (NacosException e) {
//
//            e.printStackTrace();
//        }
//        return null;
//    }
//}


import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description Nacos权重负载均衡
 * @author coisini
 * @date Sep 22, 2021
 * @Version 1.0
 */
@Slf4j
public class NacosWeightedRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    /**
     * 读取配置文件，并初始化 NacosWeightedRule
     * @param iClientConfig
     */
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    /**
     * 权重负载均衡实现
     * @param o
     * @return
     */
    @Override
    public Server choose(Object o) {
        try {
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();

            // 想要请求的微服务的名称
            String name = loadBalancer.getName();

            // 拿到服务发现的相关API
            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();

            // NacosClient通过基于权重的负载均衡算法来选择一个实例。
            Instance instance = namingService.selectOneHealthyInstance(name);

            log.info("实例：port = {}, instance = {}", instance.getPort(), instance);
            return new NacosServer(instance);
        } catch (NacosException e) {
            return null;
        }
    }
}

