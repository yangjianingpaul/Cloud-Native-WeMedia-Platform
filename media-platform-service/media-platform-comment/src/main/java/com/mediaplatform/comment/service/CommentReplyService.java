package com.mediaplatform.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mediaplatform.model.comment.dtos.CommentReplyDto;
import com.mediaplatform.model.comment.dtos.ReplyLikeDto;
import com.mediaplatform.model.comment.dtos.ReplyLoadDto;
import com.mediaplatform.model.comment.pojos.ApReply;
import com.mediaplatform.model.common.dtos.ResponseResult;

public interface CommentReplyService extends IService<ApReply> {
    ResponseResult commentReplySave(CommentReplyDto dto);

    ResponseResult commentReplyLoad(ReplyLoadDto dto);

    ResponseResult commentReplyLike(ReplyLikeDto dto);
}
