package com.mediaplatform.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mediaplatform.model.article.pojos.ApArticleConfig;

import java.util.Map;

public interface ApArticleConfigService extends IService<ApArticleConfig> {
    /**
     * modify articles
     *
     * @param map
     */
    void updateByMap(Map map);
}
