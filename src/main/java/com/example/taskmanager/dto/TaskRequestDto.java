package com.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TaskRequestDto {
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 10, message = "Title too long")
    private String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 10, message = "Description too long")
    private String description;
    private boolean completed;
}