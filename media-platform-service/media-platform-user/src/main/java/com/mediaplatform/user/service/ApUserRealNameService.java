package com.mediaplatform.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mediaplatform.model.admin.pojos.ApUserRealname;
import com.mediaplatform.model.common.dtos.ResponseResult;
import com.mediaplatform.model.user.dtos.AuthDto;

public interface ApUserRealNameService extends IService<ApUserRealname> {
    ResponseResult authList(AuthDto dto);

    ResponseResult authFail(AuthDto dto);

    ResponseResult authPass(AuthDto dto);
}
