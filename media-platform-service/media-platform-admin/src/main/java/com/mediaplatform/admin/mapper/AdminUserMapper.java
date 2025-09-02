package com.mediaplatform.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediaplatform.model.admin.pojos.AdUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminUserMapper extends BaseMapper<AdUser> {
}