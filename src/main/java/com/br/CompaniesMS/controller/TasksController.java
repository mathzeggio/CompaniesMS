package com.br.CompaniesMS.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.CompaniesMS.model.Task;
import com.br.CompaniesMS.repository.TasksRepository;

@RestController
@RequestMapping("/tasks")
@Validated
public class TasksController {
    
    @Autowired
    private TasksRepository tasksRepository;


    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = tasksRepository.findAll();
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        UUID uuid = UUID.randomUUID();
        task.setId(uuid);
        Task savedTask = tasksRepository.save(task);
        return new ResponseEntity<>(savedTask, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable @NotNull String id) {
        UUID uuid = UUID.fromString(id);
        Task task = tasksRepository.findById(uuid).orElse(null);
        if (task != null) {
            return new ResponseEntity<>(task, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable @NotNull String id, @Valid @RequestBody Task taskDetails) {
        UUID uuid = UUID.fromString(id);
        Task task = tasksRepository.findById(uuid).orElse(null);
        if (task != null) {
            task.setDescription(taskDetails.getDescription());
            task.setStatus(taskDetails.getStatus());
            Task updatedTask = tasksRepository.save(task);
            return new ResponseEntity<>(updatedTask, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable @NotNull String id) {
        UUID uuid = UUID.fromString(id);
        Task task = tasksRepository.findById(uuid).orElse(null);
        if (task != null) {
            tasksRepository.delete(task);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
