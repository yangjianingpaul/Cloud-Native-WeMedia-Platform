package com.mediaplatform.behavior.service;

import com.mediaplatform.model.behavior.dtos.ReadBehaviorDto;
import com.mediaplatform.model.common.dtos.ResponseResult;

public interface ApReadBehaviorService {

    /**
     * save the act of reading
     * @param dto
     * @return
     */
    public ResponseResult readBehavior(ReadBehaviorDto dto);
}
