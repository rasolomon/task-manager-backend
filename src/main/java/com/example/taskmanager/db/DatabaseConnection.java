package com.example.taskmanager.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/task_manager";

    private static final String USER =
            "yelen";

    private static final String PASSWORD =
            "";

    public static Connection connect() {

        try {

            Connection conn =
                    DriverManager.getConnection(
                            URL,
                            USER,
                            PASSWORD
                    );

            System.out.println(
                    "Connected to PostgreSQL successfully!"
            );

            return conn;

        } catch (SQLException e) {

            System.out.println(
                    "Connection failed!"
            );

            e.printStackTrace();

            return null;
        }
    }
}