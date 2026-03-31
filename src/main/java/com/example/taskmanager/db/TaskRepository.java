package com.example.taskmanager.db;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TaskRepository {

    public static void getAllTasks() {

        try {

            Connection conn =
                    DatabaseConnection.connect();

            Statement stmt =
                    conn.createStatement();

            ResultSet rs =
                    stmt.executeQuery(
                            "SELECT * FROM tasks"
                    );

            while (rs.next()) {

                System.out.print(rs.getInt("id") + ", ");
                System.out.print(rs.getString("title") + ", ");
                System.out.println(rs.getString("description"));

            }

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
    public static void addTask(String title, String description) {

        try {

            Connection conn =
                    DatabaseConnection.connect();

            Statement stmt =
                    conn.createStatement();

            String sql =
                    "INSERT INTO tasks (title, description) " +
                            "VALUES ('" + title + "', '" + description + "')";

            stmt.executeUpdate(sql);

            System.out.println(
                    "Task inserted successfully!"
            );

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
    public static void updateTaskTitle(
            int id,
            String newTitle
    ) {

        try {

            Connection conn =
                    DatabaseConnection.connect();

            Statement stmt =
                    conn.createStatement();

            String sql =
                    "UPDATE tasks " +
                            "SET title = '" + newTitle + "' " +
                            "WHERE id = " + id;

            int rowsUpdated =
                    stmt.executeUpdate(sql);

            System.out.println(
                    rowsUpdated + " row updated!"
            );

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}