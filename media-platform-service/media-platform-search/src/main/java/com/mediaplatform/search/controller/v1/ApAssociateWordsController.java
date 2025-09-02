package com.mediaplatform.search.controller.v1;


import com.mediaplatform.model.common.dtos.ResponseResult;
import com.mediaplatform.model.search.dtos.UserSearchDto;
import com.mediaplatform.search.service.ApAssociateWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/associate")
public class ApAssociateWordsController {

    @Autowired
    private ApAssociateWordsService apAssociateWordsService;

    @PostMapping("/search")
    public ResponseResult associateSearch(@RequestBody UserSearchDto dto) {
        return apAssociateWordsService.associateSearch(dto);
    }
}
