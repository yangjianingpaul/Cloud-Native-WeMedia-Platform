package com.mediaplatform.model.comment.dtos;

import lombok.Data;

@Data
public class CommentLikeDto {

    Long commentId;

    Integer operation;
}
