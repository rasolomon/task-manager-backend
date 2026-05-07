package com.example.taskmanager.model;

public class Task {
    private int id;
    private String title;
    private String description;
    private boolean completed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Task() {
    }

    public Task(int id, String title, String description, boolean completed) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    public void markCompleted() {
        completed = true;
    }

    @Override
    public String toString() {
        return id + ": " + description + (completed ? " [DONE]" : " [TODO]");
    }
}