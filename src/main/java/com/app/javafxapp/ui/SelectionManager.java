package com.app.javafxapp.ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class SelectionManager extends Pane {

    private final Button yes;
    private final Button no;

    public SelectionManager() {
        HBox hbox = new HBox();
        yes = new Button("Yes");
        yes.setStyle("-fx-font-size:30");

        no = new Button("No");
        no.setStyle("-fx-font-size:30");

        hbox.getChildren().addAll(yes, no);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.BOTTOM_CENTER);
        hbox.prefWidthProperty().bind(widthProperty());

        getChildren().add(hbox);
        setVisible(false);
    }

    public void addYesSelectionListener(EventHandler<ActionEvent> e) {
        yes.setOnAction(e);
    }

    public void addNoSelectionListener(EventHandler<ActionEvent> e) {
        no.setOnAction(e);
    }


}
