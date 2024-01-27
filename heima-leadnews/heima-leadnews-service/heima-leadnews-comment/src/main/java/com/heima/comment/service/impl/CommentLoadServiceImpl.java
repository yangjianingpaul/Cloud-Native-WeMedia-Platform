package com.heima.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.comment.mapper.CommentMapper;
import com.heima.comment.service.CommentLoadService;
import com.heima.model.comment.dtos.CommentLoadDto;
import com.heima.model.comment.dtos.CommentSaveDto;
import com.heima.model.comment.pojos.ApComment;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class CommentLoadServiceImpl extends ServiceImpl<CommentMapper, ApComment> implements CommentLoadService {

    /**
     * get the list of comment
     * @param dto
     * @return
     */
    @Override
    public ResponseResult loadList(CommentLoadDto dto) {
        LambdaQueryWrapper<ApComment> wrapper = new LambdaQueryWrapper<ApComment>().eq(ApComment::getArticleId, dto.getArticleId());
        ApComment apComment = getOne(wrapper);
        if (apComment == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        return ResponseResult.okResult(apComment.getContent());
    }

    /**
     * create comment
     * @param dto
     * @return
     */
    @Override
    public ResponseResult saveComment(CommentSaveDto dto) {
        if (dto.getArticleId().equals(null) || dto.getContent().equals(null)) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        ApComment comment = new ApComment();
        comment.setArticleId(dto.getArticleId());
        comment.setContent(dto.getContent());
        comment.setCreatedTime(new Date());
        boolean result = save(comment);
        return ResponseResult.okResult(result);
    }
}
