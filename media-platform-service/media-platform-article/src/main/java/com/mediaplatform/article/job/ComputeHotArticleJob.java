package com.mediaplatform.article.job;

import com.mediaplatform.article.service.HotArticleService;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ComputeHotArticleJob {

    @Autowired
    private HotArticleService hotArticleService;

    @XxlJob("computeHotArticleJob")
    public void handle() {
        log.info("Hot article score calculation The scheduling task starts...");
        hotArticleService.computeHotArticle();
        log.info("The hot article score calculation scheduling task is complete. Procedure...");
    }
}