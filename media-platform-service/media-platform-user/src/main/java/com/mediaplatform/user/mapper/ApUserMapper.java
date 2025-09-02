package com.mediaplatform.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediaplatform.model.user.pojos.ApUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApUserMapper extends BaseMapper<ApUser> {
}
