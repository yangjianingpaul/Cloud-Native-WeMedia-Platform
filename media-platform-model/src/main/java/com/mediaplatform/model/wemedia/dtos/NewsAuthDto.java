package com.mediaplatform.model.wemedia.dtos;

import com.mediaplatform.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class NewsAuthDto extends PageRequestDto {

    /**
     * title
     */
    private String title;
    /**
     * status
     */
    private Short status;
    /**
     * Id of the we-media article
     */
    private Integer id;
    /**
     * Reasons for audit failure
     */
    private String msg;
}