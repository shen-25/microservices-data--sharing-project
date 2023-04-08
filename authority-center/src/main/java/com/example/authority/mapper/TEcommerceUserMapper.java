package com.example.authority.mapper;

import com.example.authority.entity.TEcommerceUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TEcommerceUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TEcommerceUser record);

    int insertSelective(TEcommerceUser record);

    TEcommerceUser selectByPrimaryKey(Long id);

    TEcommerceUser selectByUsername(@Param("username") String username);

    TEcommerceUser selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    int updateByPrimaryKeySelective(TEcommerceUser record);

    int updateByPrimaryKey(TEcommerceUser record);

}