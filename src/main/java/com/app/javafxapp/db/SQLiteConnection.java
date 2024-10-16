package com.app.javafxapp.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class SQLiteConnection {
    public static Connection connect() {
        Connection connection = null;
        try {
            String url = "jdbc:sqlite:tickpicdb.db";
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

