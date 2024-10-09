package com.app.javafxapp.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SelectionManager  extends Pane {

    public SelectionManager(Sidebar sidebar) {
        HBox hbox = new HBox();
        Button yes = new Button("Yes");
        yes.setStyle("-fx-font-size:30");
        yes.setBackground(new Background(new BackgroundFill(
            Color.GREEN,
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));

        Button no = new Button("No");
        no.setStyle("-fx-font-size:30");
        no.setBackground(new Background(new BackgroundFill(
            Color.RED,
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));

        yes.setOnAction(e -> sidebar.setSelected(true));
        no.setOnAction(e -> sidebar.setSelected(false));

        hbox.getChildren().addAll(yes, no);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        hbox.prefWidthProperty().bind(widthProperty());

        getChildren().add(hbox);
    }
}
