package com.app.javafxapp.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

public class ImageRenderer extends HBox {
    private ImageView imageView;
    private final Button left;
    private final Button right;
    public ImageRenderer() {
        this.imageView = new ImageView();
        left = new Button("<");
        left.setStyle("-fx-font-size:25");



        right = new Button(">");
        right.setStyle("-fx-font-size:25");

        setAlignment(Pos.CENTER);
        imageView.fitWidthProperty().bind(widthProperty().divide(1.20));
        imageView.fitHeightProperty().bind(heightProperty().divide(1.20));

        getChildren().addAll(left, imageView, right);
        setSpacing(10);

        HBox.setHgrow(imageView, Priority.ALWAYS);
        HBox.setHgrow(left, Priority.NEVER);
        HBox.setHgrow(right, Priority.NEVER);
    }

    public void load(String url) {
        Image image = new Image(url);
        imageView.setImage(image);
    }

    public void addLeftArrowClickListener(EventHandler<ActionEvent> e) {
        left.setOnAction(e);
    }

    public void addRightArrowClickListener(EventHandler<ActionEvent> e) {
        right.setOnAction(e);
    }
}
