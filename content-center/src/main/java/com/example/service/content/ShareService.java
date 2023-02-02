package com.example.service.content;


import com.example.dao.share.ShareMapper;
import com.example.domain.dto.content.ShareDTO;
import com.example.domain.dto.user.UserDTO;
import com.example.domain.entity.share.Share;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service

@Slf4j
public class ShareService {


    @Autowired
    private ShareMapper shareMapper;

//    @Autowired
//    private DiscoveryClient discoveryClient;


    @Autowired
    private RestTemplate restTemplate;

    public ShareDTO findById(Integer id) {
        Share share =  shareMapper.selectByPrimaryKey(id);
        Integer userId = share.getUserId();
        // List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
        // List<String> targetURL = instances.stream()
        // .map(serviceInstance -> serviceInstance.getUri().toString() + "/users/{id}")
        // .collect(Collectors.toList());
        // int i = ThreadLocalRandom.current().nextInt(targetURL.size());
        // log.info("请求的地址：[{}]", targetURL.get(i));
        UserDTO userDTO = this.restTemplate.getForObject("http://user-center/users/{id}",
                UserDTO.class, userId);

        //消息装配

        ShareDTO shareDTO = new ShareDTO();

        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());

        return shareDTO;

    }


}
