package com.mediaplatform.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mediaplatform.model.common.dtos.ResponseResult;
import com.mediaplatform.model.wemedia.dtos.WmLoginDto;
import com.mediaplatform.model.wemedia.pojos.WmUser;

public interface WmUserService extends IService<WmUser> {

    /**
     * we-media login
     * @param dto
     * @return
     */
    public ResponseResult login(WmLoginDto dto);

}