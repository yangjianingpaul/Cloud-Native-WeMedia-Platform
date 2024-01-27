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
import com.heima.model.user.pojos.ApUser;
import com.heima.utils.thread.AppThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
        LambdaQueryWrapper<ApComment> wrapper = new LambdaQueryWrapper<ApComment>()
                .eq(ApComment::getArticleId, dto.getArticleId())
                .orderByDesc(ApComment::getCreatedTime);
        List<ApComment> commentList = list(wrapper);
        if (commentList.size() < dto.getIndex()) {
            return ResponseResult.errorResult(AppHttpCodeEnum.COMMENT_NOT_EXIST);
        }
        return ResponseResult.okResult(commentList);
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

        ApUser user = AppThreadLocalUtil.getUser();
        ApComment comment = new ApComment();
        comment.setArticleId(dto.getArticleId());
        comment.setContent(dto.getContent());
        comment.setCreatedTime(new Date());
        comment.setLikes(0);
        comment.setReply(0);
        comment.setAuthorName(user.getName());

        boolean result = save(comment);
        return ResponseResult.okResult(result);
    }
}
