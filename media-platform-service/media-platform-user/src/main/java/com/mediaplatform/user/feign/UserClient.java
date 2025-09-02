package com.mediaplatform.user.feign;

import com.mediaplatform.apis.user.IUserClient;
import com.mediaplatform.model.common.dtos.ResponseResult;
import com.mediaplatform.user.service.ApUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserClient implements IUserClient {

    @Autowired
    private ApUserService apUserService;

    @GetMapping("/api/v1/user/{userId}}")
    @Override
    public ResponseResult getUserName(Integer userId) {
        return apUserService.getUserName(userId);
    }
}
