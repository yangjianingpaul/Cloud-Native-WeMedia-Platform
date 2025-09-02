package com.mediaplatform.article.feign;

import com.mediaplatform.apis.article.IArticleClient;
import com.mediaplatform.article.service.impl.ApArticleServiceImpl;
import com.mediaplatform.model.article.dtos.ArticleDto;
import com.mediaplatform.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ArticleClient implements IArticleClient {

    @Autowired
    private ApArticleServiceImpl apArticleService;

    @PostMapping("/api/v1/article/save")
    @Override
    public ResponseResult saveArticle(@RequestBody ArticleDto dto) {
        return apArticleService.saveArticle(dto);
    }

    @GetMapping("/api/v1/article/del/{id}")
    @Override
    public ResponseResult deleteArticle(@PathVariable String id) {
        return apArticleService.deleteArticleById(id);
    }
}
