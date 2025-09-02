package com.mediaplatform.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mediaplatform.model.common.dtos.ResponseResult;
import com.mediaplatform.model.user.dtos.LoginDto;
import com.mediaplatform.model.user.pojos.ApUser;

public interface ApUserService extends IService<ApUser> {
    /**
     * app login
     *
     * @param dto
     * @return
     */
    public ResponseResult login(LoginDto dto);

    ResponseResult getUserName(Integer userId);
}
