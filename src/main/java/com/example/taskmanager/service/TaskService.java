package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskRequestDto;
import com.example.taskmanager.dto.TaskResponseDto;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.entity.Task;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository jpa;

    public Task addTask(TaskRequestDto taskRequestDto ) {
        Task task = convertToEntity(taskRequestDto);
        return jpa.save(task);
    }

    //TODO:  Use jpa.findAll(Sort.by(Sort.Direction.DESC, "id")); when working...
    public List<TaskResponseDto>  getAllTasks() {
        List<TaskResponseDto> taskDtos =  jpa.findAll(Sort.by("title"))
                .stream()
                .map(this::convertToDto)
                .toList();
        return taskDtos;
    }

    @Transactional
    public ResponseEntity<String> completeTask(int taskId) {
        Task task = jpa.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found with id " + taskId));
        task.setCompleted(true);
        return ResponseEntity.ok("Task Completed");
    }

    @Transactional
    public ResponseEntity<String> makeTaskPending(int taskId) {
        Task task = jpa.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found with id " + taskId));
        task.setCompleted(false);
        return ResponseEntity.ok("Task Pending");
    }

    public TaskResponseDto getTaskById(int taskId) {
        Task task =  jpa.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found with id " + taskId));

        return (convertToDto(task));
    }


    public List<TaskResponseDto> getCompletedTasks() {
        return jpa.findAll(Sort.by("title")).stream()
                .filter(Task::isCompleted)
                .map(this::convertToDto)
                .toList();
    }
    public List<TaskResponseDto> getPendingTasks() {
        return jpa.findAll(Sort.by("title")).stream()
                .filter(task -> !task.isCompleted())
                .map(this::convertToDto)
                .toList();
    }

    public ResponseEntity<String> deleteTask(int id) {
        Task task = jpa.findById(id).orElseThrow(() ->
                new TaskNotFoundException("Task not found with id " + id));
        jpa.delete(task);
        return ResponseEntity.ok("Task Deleted");
    }

    public ResponseEntity<String> updateTask(int id, TaskRequestDto taskRequestDto) {
        Task existingTask = jpa.findById(id).orElseThrow(() ->
                new TaskNotFoundException("Task not found with id " + id));
        Task task = convertToEntity(taskRequestDto);
        if (id == existingTask.getId()) {
            task.setId(existingTask.getId());
            jpa.save(task);
            return ResponseEntity.ok("Task Updated");
        }
        return ResponseEntity.badRequest().body("ID mismatch");
    }

    public TaskResponseDto convertToDto(Task task) {

        TaskResponseDto dto = new TaskResponseDto();

        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setCompleted(task.isCompleted());
        dto.setDescription(task.getDescription());

        return dto;
    }

    public Task convertToEntity(TaskRequestDto dto) {

        Task task = new Task();

        task.setTitle(dto.getTitle());
        task.setCompleted(dto.isCompleted());
        task.setDescription(dto.getDescription());

        return task;
    }
}