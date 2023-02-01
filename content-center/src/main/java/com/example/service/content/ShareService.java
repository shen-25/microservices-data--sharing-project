package com.example.service.content;


import com.example.dao.share.ShareMapper;
import com.example.domain.dto.content.ShareDTO;
import com.example.domain.dto.user.UserDTO;
import com.example.domain.entity.share.Share;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service

@Slf4j
public class ShareService {


    @Autowired
    private ShareMapper shareMapper;

    @Autowired
    private RestTemplate restTemplate;
    public ShareDTO findById(Integer id) {
        Share share =  shareMapper.selectByPrimaryKey(id);
        Integer userId = share.getUserId();
        UserDTO userDTO = restTemplate.getForObject("http://localhost/users/{id}",
                UserDTO.class, userId);

        //消息装配

        ShareDTO shareDTO = new ShareDTO();

        BeanUtils.copyProperties(share, shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());

        return shareDTO;

    }


}
