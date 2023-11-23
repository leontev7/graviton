package com.leontev.graviton.service;

import com.leontev.graviton.model.Task;
import com.leontev.graviton.model.TaskComplexity;
import com.leontev.graviton.model.TaskTemplate;
import com.leontev.graviton.model.TaskType;
import com.leontev.graviton.pojo.TaskTemplatePojo;
import com.leontev.graviton.repo.TaskRepository;
import com.leontev.graviton.repo.TaskTemplateRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskTemplateRepository taskTemplateRepository;
    private final TaskRepository taskRepository;



    @Override
    public TaskTemplate create(TaskTemplatePojo pojo) {

        if (pojo.getType() < 0 || pojo.getType() > 4 || pojo.getComplexity() < 0 || pojo.getComplexity() > 3)
            throw new RuntimeException();

        TaskTemplate taskTemplate = new TaskTemplate();

        log.info(String.valueOf(pojo.getContent().length()));
        taskTemplate.setContent(pojo.getContent());

        taskTemplate.setType(Arrays.stream(TaskType.values()).filter(tt -> tt.ordinal() == pojo.getType()).findFirst().orElse(TaskType.STRING));
        taskTemplate.setComplexity(Arrays.stream(TaskComplexity.values()).filter(tc -> tc.ordinal() == pojo.getComplexity()).findFirst().orElse(TaskComplexity.EASY));

        for (var option: pojo.getOptions())
            taskTemplate.getOptions().add(option);
        for (var answer: pojo.getAnswers())
            taskTemplate.getAnswers().add(answer);

        taskTemplate = save(taskTemplate);

        log.info("new task created");

        return taskTemplate;
    }

    @Override
    public Task solveTask(Long taskId, List<String> answers) {

        Task task = taskRepository.findById(taskId).orElseThrow(EntityNotFoundException::new);

        task.setSolved(isRightAnswer(task.getAnswers(), answers));
        task.setAttemptsCount(task.getAttemptsCount() + 1);

        return save(task);
    }

    private boolean isRightAnswer(List<String> answers, List<String> tryAnswers) {
        for (var ans: answers) {
            if (!tryAnswers.contains(ans))
                return false;
        }

        for (var ans: tryAnswers) {
            if (!answers.contains(ans))
                return false;
        }

        return true;
    }


    @Transactional
    private TaskTemplate save(TaskTemplate taskTemplate) {
         return taskTemplateRepository.save(taskTemplate);
    }

    @Transactional
    private Task save(Task task) {
        return taskRepository.save(task);
    }
}
