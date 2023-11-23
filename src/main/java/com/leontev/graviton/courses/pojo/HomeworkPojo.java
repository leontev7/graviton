package com.leontev.graviton.tasks.pojo;

import com.leontev.graviton.tasks.model.Homework;
import com.leontev.graviton.tasks.model.Solution;
import lombok.Data;

import java.util.List;

@Data
public class HomeworkPojo {
    private boolean completed;
    private List<TaskPojo> tasks;
    private List<SolutionPojo> solutions;

    public HomeworkPojo(Homework homework) {
    }
}
