package com.example.app;
import java.sql.Connection;
import com.example.taskmanager.db.DatabaseConnection;
import com.example.taskmanager.db.TaskRepository;

public class TestDatabaseConnection {
    public static void main(String[] args) {

        Connection conn =
                DatabaseConnection.connect();

        if (conn != null) {

            System.out.println("Database connection works!");

            System.out.println("PRINTING OUT THE TASKS BEFORE UPDATE!!!!");
            TaskRepository.getAllTasks();

            System.out.println("\n\nUPDATING A TASK!!!");

            TaskRepository.updateTaskTitle(
                    6,
                    "Master JDBC"
            );
            System.out.println("\n\nPRINTING OUT THE TASKS AFTER UPDATE!!!!");
            TaskRepository.getAllTasks();
        }
    }
}
