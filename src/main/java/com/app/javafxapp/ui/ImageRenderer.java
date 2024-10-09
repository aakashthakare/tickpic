package com.app.javafxapp.ui;

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

    private Sidebar sidebar;

    private ImageView imageView;

    public void setSidebar(Sidebar sidebar) {
        this.sidebar = sidebar;
    }

    public ImageRenderer() {
        this.imageView = new ImageView();
        Button left = new Button("<");
        left.setStyle("-fx-font-size:25");
        left.setBackground(new Background(new BackgroundFill(
            Color.DARKGRAY,
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));

        Button right = new Button(">");
        right.setStyle("-fx-font-size:25");
        right.setBackground(new Background(new BackgroundFill(
            Color.DARKGRAY,
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));

        left.setOnAction(e -> sidebar.leftArrowClicked());
        right.setOnAction(e -> sidebar.rightArrowClicked());

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
}
