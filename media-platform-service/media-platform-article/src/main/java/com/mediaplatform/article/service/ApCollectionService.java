package com.mediaplatform.article.service;

import com.mediaplatform.model.article.dtos.CollectionBehaviorDto;
import com.mediaplatform.model.common.dtos.ResponseResult;

public interface ApCollectionService {

    /**
     * collection
     * @param dto
     * @return
     */
    public ResponseResult collection(CollectionBehaviorDto dto);
}
