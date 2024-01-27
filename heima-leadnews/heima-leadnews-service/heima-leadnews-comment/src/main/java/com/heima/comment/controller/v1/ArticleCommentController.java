package com.heima.comment.controller.v1;

import com.heima.comment.service.CommentLoadService;
import com.heima.model.comment.dtos.CommentLoadDto;
import com.heima.model.comment.dtos.CommentSaveDto;
import com.heima.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
