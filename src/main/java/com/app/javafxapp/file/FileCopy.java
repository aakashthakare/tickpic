package com.app.javafxapp.file;

import com.app.javafxapp.db.DataManager;
import com.app.javafxapp.domain.Selection;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class FileCopy {
    private FileCopy(){}
    public static int copySelected(String from, String to) {
        List<Selection> fetched = DataManager.fetchSelected(from);
        int count = 0;
        for (Selection s : fetched) {
            try {
                Files.copy(Paths.get(from, s.getFile()), Path.of(to, s.getFile()));
                count++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return count;
    }
}
