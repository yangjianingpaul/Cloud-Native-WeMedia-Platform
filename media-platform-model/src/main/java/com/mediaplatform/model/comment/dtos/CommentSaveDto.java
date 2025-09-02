package com.mediaplatform.model.comment.dtos;

import lombok.Data;

@Data
public class CommentSaveDto {
    Long articleId;

    String content;
}
