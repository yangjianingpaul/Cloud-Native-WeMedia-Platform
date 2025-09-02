package com.mediaplatform.model.behavior.dtos;

import lombok.Data;

@Data
public class ReadBehaviorDto {

    // 文章、动态、评论等ID
    Long articleId;

    /**
     * number of times read
     */
    Short count;

    /**
     * reading time（S)
     */
    Integer readDuration;

    /**
     * reading percentage
     */
    Short percentage;

    /**
     * loading time
     */
    Short loadDuration;

}