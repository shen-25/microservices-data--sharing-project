package com.example.dao.share;

import com.example.domain.entity.share.Share;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface ShareMapper extends Mapper<Share> {
}