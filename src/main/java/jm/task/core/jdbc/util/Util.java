package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private Connection connection;

    public void connectToDB() {
        try {
            this.connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/new_db?serverTimezone=Europe/Moscow&useSSL=false",
                            "root", "root");
        } catch (SQLException e) {
            System.err.println("Connection failed!");
            System.err.println(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }


}
