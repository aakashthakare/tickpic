package com.app.javafxapp.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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

public class ImageRenderer extends HBox {
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
        Button left = new Button("<");
        Button right = new Button(">");

        left.setOnAction(e -> sidebar.leftArrowClicked());
        right.setOnAction(e -> sidebar.rightArrowClicked());

        setAlignment(Pos.CENTER);
        setBackground(new Background(new BackgroundFill(
            Color.LIGHTBLUE,
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));
//
        imageView.fitWidthProperty().bind(widthProperty().divide(0.1));
        imageView.fitHeightProperty().bind(heightProperty().divide(0.1));

        //hbox.
        getChildren().addAll(left, imageView, right);
        HBox.setHgrow(imageView, Priority.ALWAYS);
        HBox.setHgrow(left, Priority.NEVER);
        HBox.setHgrow(right, Priority.NEVER);

        //getChildren().add(hbox);
    }

    public void load(String url) {
        Image image = new Image(url);
        imageView.setImage(image);
    }


}
