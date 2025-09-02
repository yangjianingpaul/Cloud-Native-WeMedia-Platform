package com.mediaplatform.search.service;

import com.mediaplatform.model.common.dtos.ResponseResult;
import com.mediaplatform.model.search.dtos.UserSearchDto;

public interface ApAssociateWordsService {

    /**
     * search associational words
     * @param dto
     * @return
     */
    public ResponseResult associateSearch(UserSearchDto dto);
}
