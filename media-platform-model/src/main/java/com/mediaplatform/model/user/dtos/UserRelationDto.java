package com.mediaplatform.model.user.dtos;

import com.mediaplatform.model.common.annotation.IdEncrypt;
import lombok.Data;

@Data
public class UserRelationDto {

    // Article author ID
    @IdEncrypt
    Integer authorId;

    // Article id
    @IdEncrypt
    Long articleId;
    /**
     * operating mode
     * 0  Follow
     * 1  Cancel
     */
    Short operation;
}