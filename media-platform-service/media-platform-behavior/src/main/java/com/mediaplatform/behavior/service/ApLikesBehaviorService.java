package com.mediaplatform.behavior.service;

import com.mediaplatform.model.behavior.dtos.LikesBehaviorDto;
import com.mediaplatform.model.common.dtos.ResponseResult;

public interface ApLikesBehaviorService {

    /**
     * storing like data
     * @param dto
     * @return
     */
    public ResponseResult like(LikesBehaviorDto dto);
}
