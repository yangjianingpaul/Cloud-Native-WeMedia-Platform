package com.mediaplatform.user.service;


import com.mediaplatform.model.common.dtos.ResponseResult;
import com.mediaplatform.model.user.dtos.UserRelationDto;


public interface ApUserRelationService {
    /**
     * User follow/unfollow
     * @param dto
     * @return
     */
    public ResponseResult follow(UserRelationDto dto);
}