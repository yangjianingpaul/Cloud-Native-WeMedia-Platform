package com.mediaplatform.model.wemedia.vo;

import com.mediaplatform.model.wemedia.pojos.WmNews;
import lombok.Data;

@Data
public class WmNewsVo  extends WmNews {
    /**
     * author name
     */
    private String authorName;
}