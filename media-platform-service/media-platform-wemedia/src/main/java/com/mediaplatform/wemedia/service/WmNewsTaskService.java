package com.mediaplatform.wemedia.service;

import com.mediaplatform.model.wemedia.pojos.WmNews;

import java.util.Date;


public interface WmNewsTaskService {

    /**
     * Add a task to the delay queue
     * @param id  article id
     * @param publishTime  publish time and Can be used as the execution time of the task
     */
    public void addNewsToTask(Integer id, Date publishTime);


    public void scanNewsByTask();
}