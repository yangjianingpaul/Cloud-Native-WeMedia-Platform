package com.mediaplatform.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mediaplatform.model.comment.dtos.CommentLikeDto;
import com.mediaplatform.model.comment.dtos.CommentLoadDto;
import com.mediaplatform.model.comment.dtos.CommentSaveDto;
import com.mediaplatform.model.comment.pojos.ApComment;
import com.mediaplatform.model.common.dtos.ResponseResult;

/**
 * get article comment's list
 */
public interface CommentLoadService extends IService<ApComment> {
    ResponseResult loadList(CommentLoadDto dto);

    ResponseResult saveComment(CommentSaveDto dto);

    ResponseResult commentLike(CommentLikeDto dto);

    void updateReply(Integer commentId);
}
