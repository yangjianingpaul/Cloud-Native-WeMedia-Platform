package com.mediaplatform.apis.article;

import com.mediaplatform.apis.article.fallback.IArticleClientFallback;
import com.mediaplatform.model.article.dtos.ArticleDto;
import com.mediaplatform.model.common.dtos.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "leadnews-article", fallback = IArticleClientFallback.class)
public interface IArticleClient {

    @PostMapping("/api/v1/article/save")
    public ResponseResult saveArticle(@RequestBody ArticleDto dto);

    @GetMapping("/api/v1/article/del/{id}")
    public ResponseResult deleteArticle(@PathVariable String id);
}