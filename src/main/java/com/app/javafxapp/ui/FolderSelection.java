package com.app.javafxapp.ui;

import com.app.javafxapp.HelloApplication;
import com.app.javafxapp.file.FileCopy;
import java.io.File;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;

public class FolderSelection extends HBox {

    public FolderSelection(Sidebar sidebar) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");

        Label selectedPath = new Label();
        Button load = new Button("Open Folder");
        load.setOnAction(e -> {
            File selectedDirectory = directoryChooser.showDialog(HelloApplication.primaryStage);

            if (selectedDirectory != null) {
                selectedPath.setText(selectedDirectory.getAbsolutePath());
                sidebar.loadImages(selectedDirectory.toPath().toAbsolutePath().toString());
            } else {
                selectedPath.setText("");
            }
        });

        Button copyTo = new Button("Copy Selected Images To...");
        copyTo.setOnAction(e -> {
            File selectedDirectory = directoryChooser.showDialog(HelloApplication.primaryStage);

            if (selectedDirectory != null) {
                int total = FileCopy.copySelected(selectedPath.getText(), selectedDirectory.getAbsolutePath());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Files Copied!");
                alert.setContentText(String.format("%d image files are copied to %s", total, selectedDirectory.getAbsolutePath()));
                alert.showAndWait();
            } else {
                selectedPath.setText("");
            }
        });
        setPadding(new Insets(10));
        setSpacing(10);
        getChildren().addAll(load, copyTo, selectedPath);
    }
}
