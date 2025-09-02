package com.mediaplatform.model.wemedia.dtos;

import com.mediaplatform.model.common.dtos.PageRequestDto;
import lombok.Data;

@Data
public class WmMaterialDto extends PageRequestDto {

    /**
     * 1 collect
     * 0 Not collected
     */
    private Short isCollection;
}
