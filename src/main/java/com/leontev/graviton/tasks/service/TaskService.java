package com.leontev.graviton.service;

import com.leontev.graviton.model.Task;
import com.leontev.graviton.model.TaskTemplate;
import com.leontev.graviton.pojo.TaskTemplatePojo;

import java.util.List;

public interface TaskService {
    TaskTemplate create(TaskTemplatePojo pojo);
    Task solveTask(Long taskId, List<String> answers);
}
