package com.mediaplatform.comment.controller.v1;

import com.mediaplatform.comment.service.CommentLoadService;
import com.mediaplatform.model.comment.dtos.CommentLikeDto;
import com.mediaplatform.model.comment.dtos.CommentLoadDto;
import com.mediaplatform.model.comment.dtos.CommentSaveDto;
import com.mediaplatform.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comment")
public class ArticleCommentController {


    @Autowired
    private CommentLoadService commentLoadService;

    @PostMapping("/load")
    public ResponseResult loadList(@RequestBody CommentLoadDto dto) {
        return commentLoadService.loadList(dto);
    }

    @PostMapping("/save")
    public ResponseResult saveComment(@RequestBody CommentSaveDto dto) {
        return commentLoadService.saveComment(dto);
    }

    @PostMapping("/like")
    public ResponseResult commentLike(@RequestBody CommentLikeDto dto) {
        return commentLoadService.commentLike(dto);
    }
}
