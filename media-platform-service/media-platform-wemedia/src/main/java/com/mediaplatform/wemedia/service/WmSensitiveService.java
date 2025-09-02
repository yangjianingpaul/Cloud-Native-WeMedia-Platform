package com.mediaplatform.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mediaplatform.model.common.dtos.ResponseResult;
import com.mediaplatform.model.wemedia.dtos.SensitiveDto;
import com.mediaplatform.model.wemedia.pojos.WmSensitive;

public interface WmSensitiveService extends IService<WmSensitive> {
    ResponseResult sensitiveList(SensitiveDto dto);

    ResponseResult sensitiveSave(WmSensitive wmSensitive);

    ResponseResult sensitiveDelete(Integer id);

    ResponseResult sensitiveUpdate(WmSensitive wmSensitive);
}
