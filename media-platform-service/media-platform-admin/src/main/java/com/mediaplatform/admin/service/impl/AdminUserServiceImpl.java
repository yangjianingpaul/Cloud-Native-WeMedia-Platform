package com.mediaplatform.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mediaplatform.admin.mapper.AdminUserMapper;
import com.mediaplatform.admin.service.AdminUserService;
import com.mediaplatform.model.admin.dtos.AdUserDto;
import com.mediaplatform.model.admin.pojos.AdUser;
import com.mediaplatform.model.common.dtos.ResponseResult;
import com.mediaplatform.model.common.enums.AppHttpCodeEnum;
import com.mediaplatform.utils.common.AppJwtUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdUser> implements AdminUserService {
    @Override
    public ResponseResult login(AdUserDto dto) {
        //1. Check parameters
        if(StringUtils.isBlank(dto.getName()) || StringUtils.isBlank(dto.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"Username or password is empty");
        }

        //2. Query user
        AdUser adUser = getOne(Wrappers.<AdUser>lambdaQuery().eq(AdUser::getName, dto.getName()));
        if(adUser == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        //3. Compare password
        String salt = adUser.getSalt();
        String pswd = dto.getPassword();
        pswd = DigestUtils.md5DigestAsHex((pswd + salt).getBytes());
        if(pswd.equals(adUser.getPassword())){
            //4. Return data with JWT
            Map<String,Object> map  = new HashMap<>();
            map.put("token", AppJwtUtil.getToken(adUser.getId().longValue()));
            adUser.setSalt("");
            adUser.setPassword("");
            map.put("user",adUser);
            return ResponseResult.okResult(map);

        }else {
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
    }
}
