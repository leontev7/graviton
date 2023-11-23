package com.leontev.graviton.tasks.pojo;

import com.leontev.graviton.tasks.model.TaskTemplate;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TaskTemplatePojo {

    private long id;
    private String content;
    private int type;
    private int complexity;
    private List<String> options;
    private List<String> answers;

    public TaskTemplatePojo(TaskTemplate taskTemplate) {
        this.id = taskTemplate.getId();
        this.content = taskTemplate.getContent();
        this.type = taskTemplate.getType().ordinal();
        this.options = taskTemplate.getOptions();
        this.answers = taskTemplate.getAnswers();
        this.complexity = taskTemplate.getComplexity().ordinal();
    }
}
