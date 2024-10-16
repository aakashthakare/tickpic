package com.app.javafxapp.ui;

import com.app.javafxapp.TickPicApplication;
import com.app.javafxapp.file.FileCopy;
import java.io.File;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;

public class FolderSelection extends HBox {

    private final Label selectedPath;
    private final DirectoryChooser directoryChooser;
    private final Sidebar sidebar;
    private final Button copyTo;
    private final Button load;

    public FolderSelection(Sidebar sidebar) {
        this.sidebar = sidebar;
        directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Folder");

        selectedPath = new Label();
        selectedPath.setVisible(false);

        load = new Button("Open Folder");
        load.setOnAction(e -> folderSelection());

        copyTo = new Button("Copy Selected Images To...");
        copyTo.setOnAction(e -> copySelectedFiles());
        copyTo.setVisible(false);

        setPadding(new Insets(10));
        setSpacing(10);
        getChildren().addAll(load, copyTo, selectedPath);
        setBackground(new Background(new BackgroundFill(
            Color.ANTIQUEWHITE,
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));
    }

    private void copySelectedFiles() {
        File selectedDirectory = directoryChooser.showDialog(TickPicApplication.primaryStage);

        if (selectedDirectory != null) {
            int total = FileCopy.copySelected(selectedPath.getText(), selectedDirectory.getAbsolutePath());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Files Copied!");
            alert.setContentText(String.format("%d image files are copied to %s", total, selectedDirectory.getAbsolutePath()));
            alert.showAndWait();
        } else {
            selectedPath.setText("");
            copyTo.setVisible(false);
        }
    }

    private void folderSelection() {
        File selectedDirectory = directoryChooser.showDialog(TickPicApplication.primaryStage);

        if (selectedDirectory != null) {
            selectedPath.setText(selectedDirectory.getAbsolutePath());
            sidebar.loadImages(selectedDirectory.toPath().toAbsolutePath().toString());
            copyTo.setVisible(true);
        } else {
            selectedPath.setText("");
        }
    }
}
