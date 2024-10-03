package com.app.javafxapp.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ImageRenderer extends Pane {
    private Sidebar sidebar;

    public void setSidebar(Sidebar sidebar) {
        this.sidebar = sidebar;
    }

    // image
    // left-right navigation
    // keyboard shortcut for navigation

    private ImageView imageView;

    public ImageRenderer() {
        this.imageView = new ImageView();

        HBox hbox = new HBox();
        Button left = new Button("<");
        Button right = new Button(">");

        left.setOnAction(e -> sidebar.leftArrowClicked());
        right.setOnAction(e -> sidebar.rightArrowClicked());

        hbox.setAlignment(Pos.CENTER);
        hbox.setBackground(new Background(new BackgroundFill(
            Color.LIGHTBLUE,
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));
        hbox.getChildren().addAll(left, imageView, right);

        HBox.setHgrow(imageView, Priority.ALWAYS);
        HBox.setHgrow(left, Priority.NEVER);
        HBox.setHgrow(right, Priority.NEVER);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(hbox.heightProperty().doubleValue());
        imageView.setFitWidth(hbox.widthProperty().doubleValue());

        getChildren().add(hbox);
    }

    public void load(String url) {
        Image image = new Image(url);
        imageView.setImage(image);
    }


}
