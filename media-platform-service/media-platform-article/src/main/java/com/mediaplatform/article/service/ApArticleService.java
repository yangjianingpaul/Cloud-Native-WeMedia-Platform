package com.mediaplatform.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mediaplatform.model.article.dtos.ArticleDto;
import com.mediaplatform.model.article.dtos.ArticleHomeDto;
import com.mediaplatform.model.article.dtos.ArticleInfoDto;
import com.mediaplatform.model.article.pojos.ApArticle;
import com.mediaplatform.model.common.dtos.ResponseResult;
import com.mediaplatform.model.mess.ArticleVisitStreamMess;
import org.springframework.web.bind.annotation.RequestBody;

public interface ApArticleService extends IService<ApArticle> {
    /**
     * load the list of articles
     *
     * @param dto
     * @param type 1.load more  2.load the latest
     * @return
     */
    public ResponseResult load(ArticleHomeDto dto, Short type);

    /**
     * load the list of articles
     *
     * @param dto
     * @param type      1 load more   2 load the latest
     * @param firstPage true:It's the top page,  false: non homepage
     * @return
     */
    public ResponseResult load2(ArticleHomeDto dto, Short type, boolean firstPage);

    /**
     * save relevant articles on the app
     *
     * @param dto
     * @return
     */
    public ResponseResult saveArticle(ArticleDto dto);

    /**
     * Update the score value of the article, and update the hot article data in the cache
     *
     * @param mess
     */
    public void updateScore(ArticleVisitStreamMess mess);

    /**
     * Load the article details and the data will be displayed
     *
     * @param dto
     * @return
     */
    public ResponseResult loadArticleBehavior(ArticleInfoDto dto);

    /**
     * delete related articles on the app
     *
     * @param id
     * @return
     */
    ResponseResult deleteArticleById(String id);
}
