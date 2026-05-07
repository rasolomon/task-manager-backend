package com.example.taskmanager.dao;

import com.example.taskmanager.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskDao {
    @Autowired
    private DataSource dataSource;

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            // TODO:  Change to ORDER BY ID later
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tasks ORDER BY title");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Task task = new Task(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBoolean("completed"));
                tasks.add(task);
            }
            return tasks;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Task getTaskById(int taskId) {
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM tasks WHERE id = ?");
            stmt.setInt(1, taskId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Task task = new Task(rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getBoolean("completed"));
                return task;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void addTask(Task task) {
        String sql = "INSERT INTO tasks (title, description) " +
                     "VALUES (?, ?)";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.executeUpdate();
            System.out.println("Task inserted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTask(int id, Task task ) {
        String sql =    "UPDATE tasks " +
                "SET title = ?, description = ?, completed = ? " +
                "WHERE id = ?";

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, task.getTitle());
            stmt.setString(2, task.getDescription());
            stmt.setBoolean(3, task.isCompleted());
            stmt.setInt(4, id);

            int rowsUpdated = stmt.executeUpdate();
            System.out.println(rowsUpdated + " row updated!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int id) {
        String sql =    "DELETE FROM tasks " +
                "WHERE id = ?";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();
            System.out.println(rowsDeleted + " row deleted!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void completeTask(int taskId) {
        String sql =    "UPDATE tasks SET completed = true WHERE id = ?";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, taskId);

            int rowsUpdated = stmt.executeUpdate();
            System.out.println(rowsUpdated + " task completed!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void makeTaskPending(int taskId) {
        String sql =    "UPDATE tasks SET completed = false WHERE id = ?";
        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, taskId);

            int rowsUpdated = stmt.executeUpdate();
            System.out.println(rowsUpdated + " task pending!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}