package com.example.taskmanager.service;

import com.example.taskmanager.dao.TaskDao;
import com.example.taskmanager.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskDao dao;

    public ResponseEntity<String> addTask(Task task ) {
        dao.addTask(task);
        return ResponseEntity.ok("Task Inserted");
    }

    public List<Task>  getAllTasks() {
        return dao.getAllTasks();
    }

    public ResponseEntity<String> completeTask(int taskId) {
        Task task = getTaskById(taskId);
        if (task != null) {
            dao.completeTask(taskId);
            return ResponseEntity.ok("Task Completed");
        }
        return ResponseEntity.status(404).body("Task not found");
    }

    public ResponseEntity<String> makeTaskPending(int taskId) {
        Task task = getTaskById(taskId);
        if (task != null) {
            dao.makeTaskPending(taskId);
            return ResponseEntity.ok("Task Pending");
        }
        return ResponseEntity.status(404).body("Task not found");
    }

    public Task getTaskById(int taskId) {
        return dao.getTaskById(taskId);
    }


    public List<Task> getCompletedTasks() {
        return dao.getAllTasks().stream()
                .filter(Task::isCompleted)
                .toList();
    }
    public List<Task> getPendingTasks() {
        return dao.getAllTasks().stream()
                .filter(task -> !task.isCompleted())
                .toList();
    }


    public void updateTaskDescription(int taskId, String newDescription) {
        Task task = getTaskById(taskId);
        if (task != null) {
            task.setDescription(newDescription);
        }
    }


    public ResponseEntity<String> deleteTask(int id) {
        dao.deleteTask(id);
        return ResponseEntity.ok("Task Deleted");
    }

    public ResponseEntity<String> updateTask(int id, Task task) {
        dao.updateTask(id, task);
        return ResponseEntity.ok("Task Updated");
    }
}