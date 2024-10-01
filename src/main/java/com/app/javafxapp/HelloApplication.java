package com.app.javafxapp;

import com.app.javafxapp.ui.ImageRenderer;
import com.app.javafxapp.ui.SelectionManager;
import com.app.javafxapp.ui.Sidebar;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        ImageRenderer renderer = new ImageRenderer();
        Sidebar sidebar = new Sidebar();
        SelectionManager selectionManager = new SelectionManager();
        selectionManager.setPrefHeight(80);

        VBox imagePane = new VBox();
        imagePane.getChildren().addAll(renderer, selectionManager);
        imagePane.setFillWidth(true);
        VBox.setVgrow(renderer, Priority.ALWAYS);
        VBox.setVgrow(selectionManager, Priority.NEVER);
        imagePane.setBackground(new Background(new BackgroundFill(
            Color.RED,
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));

        HBox main = new HBox();
        main.getChildren().addAll(sidebar, imagePane);
        HBox.setHgrow(sidebar, Priority.NEVER);
        HBox.setHgrow(imagePane, Priority.ALWAYS);

        FXMLLoader fxmlLoader =
            new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(main);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}