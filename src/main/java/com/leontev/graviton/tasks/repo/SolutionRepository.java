package com.leontev.graviton.tasks.repo;

import com.leontev.graviton.tasks.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
