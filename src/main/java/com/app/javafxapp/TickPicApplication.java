package com.app.javafxapp;

import com.app.javafxapp.db.DataManager;
import com.app.javafxapp.ui.FolderSelection;
import com.app.javafxapp.ui.ImageRenderer;
import com.app.javafxapp.ui.SelectionManager;
import com.app.javafxapp.ui.Sidebar;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TickPicApplication extends Application {

    public static Stage primaryStage;

    private EventHandler<KeyEvent> keyEventHandler;

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(mainUI());
        stage.setTitle("TickPic");
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);
        this.primaryStage = stage;

        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEventHandler);
        DataManager.init();
    }

    private VBox mainUI() {
        ImageRenderer renderer = new ImageRenderer();
        SelectionManager selectionManager = new SelectionManager();
        selectionManager.setPrefHeight(100);

        Sidebar sidebar = new Sidebar(renderer, selectionManager);
        keyEventHandler = sidebar::move;
        FolderSelection folderSelection = new FolderSelection(sidebar);

        VBox imagePane = new VBox();
        imagePane.getChildren().addAll(renderer, selectionManager);

        VBox.setVgrow(renderer, Priority.ALWAYS);
        VBox.setVgrow(selectionManager, Priority.NEVER);

        HBox main = new HBox();
        main.getChildren().addAll(sidebar, imagePane);
        HBox.setHgrow(sidebar, Priority.NEVER);
        HBox.setHgrow(imagePane, Priority.ALWAYS);

        VBox mainV = new VBox();
        mainV.getChildren().addAll(folderSelection, main);
        VBox.setVgrow(folderSelection, Priority.NEVER);
        VBox.setVgrow(main, Priority.ALWAYS);
        return mainV;
    }

    public static void main(String[] args) {
        launch();
    }
}