package com.app.javafxapp.db;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
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
            pstmt.setString(1, URLEncoder.encode(folder, StandardCharsets.UTF_8));
            pstmt.setString(2, URLEncoder.encode(file, StandardCharsets.UTF_8));
            pstmt.setInt(3, isSelected ? 1 : 0);
            pstmt.setInt(4, isSelected ? 1 : 0);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Selection> fetch(String folder) {
        String sql = "SELECT * FROM selection where folder = ?";
        List<Selection> fetch = new ArrayList<>();

        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, URLEncoder.encode(folder));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Selection selection = new Selection();
                selection.setId(rs.getInt("id"));
                selection.setFolder(URLDecoder.decode(rs.getString("folder"), StandardCharsets.UTF_8));
                selection.setFile(URLDecoder.decode(rs.getString("file"), StandardCharsets.UTF_8));
                selection.setSelected(rs.getInt("isSelected") == 1);
                fetch.add(selection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fetch;
    }
}
