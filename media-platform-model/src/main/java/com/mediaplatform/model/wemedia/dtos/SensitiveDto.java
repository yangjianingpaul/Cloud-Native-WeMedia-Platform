package com.mediaplatform.model.wemedia.dtos;

import com.mediaplatform.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class SensitiveDto extends PageRequestDto {

    /**
     * Sensitive word name
     */
    private String name;
}
