package com.leontev.graviton.tasks.repo;

import com.leontev.graviton.tasks.model.TaskTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskTemplateRepository extends JpaRepository<TaskTemplate, Long> {
}
