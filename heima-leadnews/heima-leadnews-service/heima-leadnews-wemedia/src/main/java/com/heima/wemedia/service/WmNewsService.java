package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.NewsAuthDto;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;

public interface WmNewsService extends IService<WmNews> {

    /**
     * 条件查询文章列表
     * @param dto
     * @return
     */
    public ResponseResult findList(WmNewsPageReqDto dto);

    /**
     * 发布修改文章或保存为草稿
     * @param dto
     * @return
     */
    public ResponseResult submitNews(WmNewsDto dto);

    /**
     * 文章的上下架
     * @param dto
     * @return
     */
    public ResponseResult downOrUp(WmNewsDto dto);

    /**
     * 删除文章
     * @param id
     * @return
     */
    ResponseResult deleteNewsById(Integer id);

    /**
     * 文章审核列表
     * @param dto
     * @return
     */
    ResponseResult authArticleList(NewsAuthDto dto);

    /**
     * 文章人工审核
     * @param articleId
     * @return
     */
    ResponseResult articleDetails(Integer articleId);

    /**
     * 人工审核通过
     * @param dto
     * @return
     */
    ResponseResult articleAuthPass(NewsAuthDto dto);

    /**
     * 人工审核失败
     * @param dto
     * @return
     */
    ResponseResult articleAuthFail(NewsAuthDto dto);
}
