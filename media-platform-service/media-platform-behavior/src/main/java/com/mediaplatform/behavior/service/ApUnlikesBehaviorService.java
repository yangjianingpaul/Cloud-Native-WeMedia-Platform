package com.mediaplatform.behavior.service;

import com.mediaplatform.model.behavior.dtos.UnLikesBehaviorDto;
import com.mediaplatform.model.common.dtos.ResponseResult;

/**
 * <p>
 * APP doesn't like the behavior table service class
 * </p>
 *
 * @author itheima
 */
public interface ApUnlikesBehaviorService {

    /**
     * dislike
     * @param dto
     * @return
     */
    public ResponseResult unLike(UnLikesBehaviorDto dto);

}