package com.mediaplatform.comment.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mediaplatform.model.comment.pojos.ApReply;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentReplyMapper extends BaseMapper<ApReply> {
}
