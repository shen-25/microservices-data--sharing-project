package com.example.service;


import com.example.mapper.ShareMapper;
import com.example.domain.dto.ShareDTO;
import com.example.domain.dto.UserDTO;
import com.example.domain.entity.Share;
import com.example.feignclient.UserCenterFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

@Slf4j
public class ShareService {


    @Autowired
    private ShareMapper shareMapper;

//    @Autowired
//    private DiscoveryClient discoveryClient;


//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private UserCenterFeignClient userCenterFeignClient;

    public ShareDTO findById(Integer id) {
        Share share =  shareMapper.selectByPrimaryKey(id);
        Integer userId = share.getUserId();
        // List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
        // List<String> targetURL = instances.stream()
        // .map(serviceInstance -> serviceInstance.getUri().toString() + "/users/{id}")
        // .collect(Collectors.toList());
        // int i = ThreadLocalRandom.current().nextInt(targetURL.size());
        // log.info("请求的地址：[{}]", targetURL.get(i));
//        UserDTO userDTO = this.restTemplate.getForObject("http://user-center/users/{id}",
//                UserDTO.class, userId);

        // feign
        UserDTO userDTO = userCenterFeignClient.findById(id);

        //消息装配

        ShareDTO shareDTO = new ShareDTO();

        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());

        return shareDTO;

    }


}
