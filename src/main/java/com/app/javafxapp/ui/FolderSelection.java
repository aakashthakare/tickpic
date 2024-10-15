package com.app.javafxapp.ui;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class FolderSelection extends HBox {

    public FolderSelection(Sidebar sidebar) {
        TextField path = new TextField("Folder");
        Button load = new Button("Load");
        load.setOnAction(e -> sidebar.loadImages(path.getText()));

        getChildren().addAll(path, load);
    }
}
