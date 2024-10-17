package com.app.javafxapp.db;

import com.app.javafxapp.domain.Selection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    public static void init() {
        String sql = "CREATE TABLE IF NOT EXISTS selection (\n"
            + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + " folder TEXT NOT NULL,\n"
            + " file TEXT NOT NULL,\n"
            + " isSelected INTEGER NOT NULL,\n"
            + " UNIQUE(folder, file)\n"
            + ");";

        try (Connection conn = SQLiteConnection.connect();
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void save(String folder, String file, boolean isSelected) {
        String sql = "INSERT INTO selection (folder, file, isSelected)\n" +
            "VALUES (?, ?, ?)\n" +
            "ON CONFLICT(folder, file)\n" +
            "DO UPDATE SET isSelected = ?;\n";

        try (Connection conn = SQLiteConnection.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, folder);
            pstmt.setString(2, file);
            pstmt.setInt(3, isSelected ? 1 : 0);
            pstmt.setInt(4, isSelected ? 1 : 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Selection> fetchSelected(String folder) {
        String sql = "SELECT * FROM selection where folder = ? AND isSelected = 1";
        List<Selection> fetch = new ArrayList<>();

        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, folder);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Selection selection = new Selection();
                selection.setId(rs.getInt("id"));
                selection.setFolder(rs.getString("folder"));
                selection.setFile(rs.getString("file"));
                selection.setSelected(rs.getInt("isSelected") == 1);
                fetch.add(selection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fetch;
    }
}
