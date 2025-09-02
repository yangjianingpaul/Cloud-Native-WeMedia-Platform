package com.mediaplatform.model.comment.dtos;

import lombok.Data;

@Data
public class CommentReplyDto {
    Integer commentId;

    String content;
}
