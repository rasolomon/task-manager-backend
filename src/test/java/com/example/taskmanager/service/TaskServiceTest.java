package com.example.taskmanager.service;

import com.example.taskmanager.dto.TaskRequestDto;
import com.example.taskmanager.dto.TaskResponseDto;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.repository.TaskRepository;
import com.example.taskmanager.entity.Task;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository jpa;

    @InjectMocks
    private TaskService service;

    private List<Task> tasks;

    @Test
    void shouldAddTask() {
        Task task = new Task();
        TaskRequestDto taskRequestDto = new TaskRequestDto();


        // Cant use plain task because the task returned is a different one due to the convertToEntity() in the service class
        when(jpa.save(any(Task.class))).thenReturn(task);

        service.addTask(taskRequestDto);

        verify(jpa).save(any(Task.class));
    }

    @Test
    void shouldCompleteTask() {
        Task task = new  Task(10, "Task 10", "Description 10", false);
        when(jpa.findById(10))
                .thenReturn(Optional.of(task));

        service.completeTask(10);
        assertTrue(task.isCompleted());
    }

    @Test
    void shouldRemoveTask() {
        Task task = new Task();

        when(jpa.findById(1))
                .thenReturn(Optional.of(task));

        service.deleteTask(1);

        verify(jpa).delete(task);

    }

    @Test
    void shouldReturnNullWhenTaskDoesNotExist() {
        when(jpa.findById(12345))
                .thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> {
            service.completeTask(12345);
        });
    }

    @Test
    void shouldReturnCompletedTasks() {
        List<Task> tasks = List.of(
            new Task(1, "A", "", true),
            new Task(2, "B", "", false)
        );

        when(jpa.findAll(Sort.by("title")))
                .thenReturn(tasks);

        List<TaskResponseDto> completed = service.getCompletedTasks();

        assertEquals(1, completed.size());
        assertTrue(completed.get(0).isCompleted());
    }



    @Test
    void shouldReturnPendingTasks() {
        List<Task> tasks = List.of(
                new Task(1, "A", "", true),
                new Task(2, "B", "", false)
        );

        when(jpa.findAll(Sort.by("title")))
                .thenReturn(tasks);

        List<TaskResponseDto> pending = service.getPendingTasks();

        assertEquals(1, pending.size());
        assertFalse(pending.get(0).isCompleted());
    }



    @Test
    void shouldDoNothingWhenCompletingNonExistentTask() {
        List<Task> tasks = List.of(
                new Task(1, "A", "", true),
                new Task(2, "B", "", false)
        );

        when(jpa.findById(1234))
                .thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> {
            service.completeTask(1234);
        });
    }


}
