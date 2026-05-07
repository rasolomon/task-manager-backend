package com.example.taskmanager.controller;

import com.example.taskmanager.dao.TaskDao;
import com.example.taskmanager.model.Task;
import com.example.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TaskController {

    @Autowired
    TaskService service;

    @GetMapping("/tasks")
    public List<Task> getAllTasks() {
        return service.getAllTasks();
    }

    @PostMapping("/tasks")
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        return service.addTask(task);
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<String> updateTask(@PathVariable int id, @RequestBody Task task ) {
        return service.updateTask(id, task);
    }

    @GetMapping("/tasks/{id}")
    public Task getTaskById(@PathVariable int id) {
        return service.getTaskById(id);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        return service.deleteTask(id);
    }

    @GetMapping("/tasks/completed")
    public List<Task> getCompletedTasks() {
        return service.getCompletedTasks();
    }

    @GetMapping("/tasks/pending")
    public List<Task> getPendingTasks() {
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