package com.app.javafxapp;

import com.app.javafxapp.ui.ImageRenderer;
import com.app.javafxapp.ui.SelectionManager;
import com.app.javafxapp.ui.Sidebar;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ImageRenderer renderer = new ImageRenderer();
        Sidebar sidebar = new Sidebar();
        SelectionManager selectionManager = new SelectionManager();

        VBox imagePane = new VBox();
        imagePane.getChildren().addAll(renderer, selectionManager);

        HBox main = new HBox();
        main.getChildren().addAll(sidebar, imagePane);

        FXMLLoader fxmlLoader =
            new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(main, 820, 740);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        renderer.load("/Users/akash/thoughtworks/Work/Source/Learn/tickpic/src/main/resources/images/img.png");
    }

    public static void main(String[] args) {
        launch();
    }
}