package com.mediaplatform.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mediaplatform.model.admin.dtos.AdUserDto;
import com.mediaplatform.model.admin.pojos.AdUser;
import com.mediaplatform.model.common.dtos.ResponseResult;

public interface AdminUserService extends IService<AdUser> {

    /**
     * Self-media platform login
     * @param dto
     * @return
     */
    public ResponseResult login(AdUserDto dto);

}