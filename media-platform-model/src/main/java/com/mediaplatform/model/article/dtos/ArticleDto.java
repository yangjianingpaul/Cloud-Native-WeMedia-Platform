package com.mediaplatform.model.article.dtos;

import com.mediaplatform.model.article.pojos.ApArticle;
import lombok.Data;

@Data
public class ArticleDto extends ApArticle {
    /**
     * article content
     */
    private String content;
}
