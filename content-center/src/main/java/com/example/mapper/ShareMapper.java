package com.example.mapper;

import com.example.domain.entity.Share;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ShareMapper extends Mapper<Share> {
}