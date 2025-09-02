package com.mediaplatform.search.service;

import com.mediaplatform.model.search.dtos.UserSearchDto;
import com.mediaplatform.model.common.dtos.ResponseResult;

import java.io.IOException;

public interface ArticleSearchService {

    /**
     * es article pagination search
     *
     * @return
     */
    ResponseResult searchArticle(UserSearchDto userSearchDto) throws IOException;
}