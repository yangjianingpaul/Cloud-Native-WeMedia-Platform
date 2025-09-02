package com.mediaplatform.model.wemedia.dtos;

import com.mediaplatform.model.common.dtos.PageRequestDto;
import lombok.Data;

import java.util.Date;

@Data
public class WmNewsPageReqDto extends PageRequestDto {

    /**
     * status
     */
    private Short status;
    /**
     * begin time
     */
    private Date beginPubDate;
    /**
     * end time
     */
    private Date endPubDate;
    /**
     * channel ID
     */
    private Integer channelId;
    /**
     * keywords
     */
    private String keyword;
}