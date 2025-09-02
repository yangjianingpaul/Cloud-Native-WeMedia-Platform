package com.mediaplatform.model.article.vos;

import com.mediaplatform.model.article.pojos.ApArticle;
import lombok.Data;

@Data
public class HotArticleVo extends ApArticle {
    /**
     * article score
     */
    private Integer score;
}