package com.mediaplatform.admin.controller.v1;

import com.mediaplatform.admin.service.AdminUserService;
import com.mediaplatform.model.admin.dtos.AdUserDto;
import com.mediaplatform.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LogController {

    @Autowired
    private AdminUserService adminUserService;

    @PostMapping("/in")
    public ResponseResult login(@RequestBody AdUserDto dto){
        return adminUserService.login(dto);
    }
}
