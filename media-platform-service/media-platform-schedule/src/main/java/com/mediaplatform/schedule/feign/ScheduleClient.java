package com.mediaplatform.schedule.feign;

import com.mediaplatform.apis.schedule.IScheduleClient;
import com.mediaplatform.model.common.dtos.ResponseResult;
import com.mediaplatform.model.schedule.dtos.Task;
import com.mediaplatform.schedule.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ScheduleClient implements IScheduleClient {

    @Autowired
    private TaskService taskService;

    /**
     * create task
     *
     * @param task task object
     * @return task id
     */
    @PostMapping("/api/v1/task/add")
    @Override
    public ResponseResult addTask(@RequestBody Task task) {
        return ResponseResult.okResult(taskService.addTask(task));
    }

    /**
     * cancel task
     *
     * @param taskId task id
     * @return cancel result
     */
    @GetMapping("/api/v1/task/{taskId}")
    @Override
    public ResponseResult cancelTask(@PathVariable("taskId") long taskId) {
        return ResponseResult.okResult(taskService.cancelTask(taskId));
    }

    /**
     * Pull tasks by type and priority
     *
     * @param type
     * @param priority
     * @return
     */
    @GetMapping("/api/v1/task/{type}/{priority}")
    @Override
    public ResponseResult poll(@PathVariable("type") int type, @PathVariable("priority") int priority) {
        return ResponseResult.okResult(taskService.poll(type, priority));
    }
}