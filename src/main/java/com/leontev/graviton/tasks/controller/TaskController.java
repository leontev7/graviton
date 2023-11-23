package com.leontev.graviton.controller;


import com.leontev.graviton.pojo.TaskTemplatePojo;
import com.leontev.graviton.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/tasks")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class TaskController {

    private final TaskService service;

    @PostMapping("/create")
    public ResponseEntity<TaskTemplatePojo> createTask(@RequestBody TaskTemplatePojo taskTemplatePojo) {
        return ResponseEntity.ok(new TaskTemplatePojo(service.create(taskTemplatePojo)));
    }

    @PostMapping("/solve")
    public ResponseEntity<TaskTemplatePojo> solveTask(@RequestBody TaskTemplatePojo taskTemplatePojo) {
        return ResponseEntity.ok(new TaskTemplatePojo(service.create(taskTemplatePojo)));
    }
}
