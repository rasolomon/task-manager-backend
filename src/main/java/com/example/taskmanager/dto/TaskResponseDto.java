package com.example.taskmanager.dto;

import lombok.Data;

@Data
public class TaskResponseDto {
    private int id;
    private String title;
    private String description;
    private boolean completed;
}
