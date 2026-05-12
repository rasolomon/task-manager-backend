package com.example.taskmanager.controller;

import com.example.taskmanager.dto.TaskRequestDto;
import com.example.taskmanager.dto.TaskResponseDto;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TaskController {

    @Autowired
    TaskService service;

    @GetMapping("/tasks/{id}")
    public TaskResponseDto getTaskById(@PathVariable int id) {
        return service.getTaskById(id);
    }

    @GetMapping("/tasks")
    public List<TaskResponseDto> getAllTasks() {
        return service.getAllTasks();
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskResponseDto> addTask(@Valid @RequestBody TaskRequestDto taskRequestDto) {
        Task task = service.addTask(taskRequestDto);
        return ResponseEntity.ok(service.convertToDto(task));
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<String> updateTask(@PathVariable int id, @Valid @RequestBody TaskRequestDto taskRequestDto ) {
        return service.updateTask(id, taskRequestDto);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        return service.deleteTask(id);
    }

    @GetMapping("/tasks/completed")
    public List<TaskResponseDto> getCompletedTasks() {
        return service.getCompletedTasks();
    }

    @GetMapping("/tasks/pending")
    public List<TaskResponseDto> getPendingTasks() {
        return service.getPendingTasks();
    }

    @PatchMapping("/tasks/{id}/complete")
    public ResponseEntity<String> completeTask(@PathVariable int id) {
        return service.completeTask(id);
    }

    @PatchMapping("/tasks/{id}/pending")
    public ResponseEntity<String> makeTaskPending(@PathVariable int id) {
        return service.makeTaskPending(id);
    }
}