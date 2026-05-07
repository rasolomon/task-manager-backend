package com.example.taskmanager.service;

import com.example.taskmanager.dao.TaskDao;
import com.example.taskmanager.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskDao dao;

    @InjectMocks
    private TaskService service;

    private List<Task> tasks;

    @BeforeEach
    void setUp() {

        tasks = new ArrayList<>();

        // Get All Tasks
        lenient().when(dao.getAllTasks()).thenReturn(tasks);

        // Add a Task
        lenient().doAnswer(invocation -> {
            Task task = invocation.getArgument(0);
            tasks.add(task);
            return null;
        }).when(dao).addTask(any(Task.class));

        // Delete a Task
        lenient().doAnswer(invocation -> {
            int id = invocation.getArgument(0);
            tasks.removeIf(t -> t.getId() == id);
            return null;
        }).when(dao).deleteTask(anyInt());

        // Get Task By ID
        lenient().when(dao.getTaskById(anyInt()))
                .thenAnswer(invocation -> {
                    int id = invocation.getArgument(0);
                    return tasks.stream()
                            .filter(t -> t.getId() == id)
                            .findFirst()
                            .orElse(null);
                });

        // Complete a Task
        lenient().doAnswer(invocation -> {
            int id = invocation.getArgument(0);
            tasks.stream()
                    .filter(t -> t.getId() == id)
                    .findFirst()
                    .ifPresent(t -> t.setCompleted(true));
            return null;
        }).when(dao).completeTask(anyInt());

        // preload ONLY service
        service.addTask(new Task(1, "Task 1", "Description 1", false));
        service.addTask(new Task(2, "Task 2", "Description 2", false));
        service.addTask(new Task(3, "Task 3", "Description 3", false));
        service.addTask(new Task(4, "Task 4", "Description 4", false));

    }

    @Test
    void shouldAddTask() {
        assertEquals(4, service.getAllTasks().size());
    }

    @Test
    void shouldCompleteTask() {
        service.completeTask(1);
        verify(dao).completeTask(1);
        assertTrue(service.getTaskById(1).isCompleted());
    }

    @Test
    void shouldRemoveTask() {
        service.deleteTask(1);
        verify(dao).deleteTask(1);
        assertNull(service.getTaskById(1));
        assertEquals(3, service.getAllTasks().size());

        service.deleteTask(123456);
        assertEquals(3, service.getAllTasks().size());

    }

    @Test
    void shouldReturnNullWhenTaskDoesNotExist() {
        assertNull(service.getTaskById(12345));
    }

    @Test
    void shouldReturnCompletedTasks() {
        service.completeTask(2);
        service.completeTask(4);
        List<Task> completed = service.getCompletedTasks();

        assertTrue(completed.stream().anyMatch(t -> t.getId() == 2));
        assertTrue(completed.stream().anyMatch(t -> t.getId() == 4));
        assertEquals(2, completed.size());
    }

    @Test
    void shouldReturnPendingTasks() {
        service.completeTask(2);
        service.completeTask(4);
        List<Task> pending = service.getPendingTasks();

        assertTrue(pending.stream().anyMatch(t -> t.getId() == 1));
        assertTrue(pending.stream().anyMatch(t -> t.getId() == 3));
        assertEquals(2, pending.size());
    }

    @Test
    void shouldUpdateTaskDescription() {
        String oldDescription = "Description 1";
        Task task = service.getTaskById(1);
        service.addTask(task);
        assertEquals(oldDescription, service.getTaskById(1).getDescription());

        String newDescription = "Rebellious subjects, enemies to peace,\n" +
                "Profaners of this neighbour-stained steel\n" +
                "Will they not hear?  What, ho!  You men, you beasts,\n" +
                "That quench the fire of your pernicious rage";

        service.updateTaskDescription(1, newDescription);
        assertEquals(newDescription, service.getTaskById(1).getDescription());
        assertFalse(service.getTaskById(1).isCompleted());
    }

    @Test
    void shouldNotCrashWhenUpdatingNonExistentTask() {
        int originalSize = service.getAllTasks().size();
        service.updateTaskDescription(123456, "blah blah");
        assertEquals(originalSize, service.getAllTasks().size());
    }

    @Test
    void shouldDoNothingWhenCompletingNonExistentTask() {
        int originalSize = service.getAllTasks().size();
        service.completeTask(123456);
        assertEquals(originalSize, service.getAllTasks().size());
    }
}
