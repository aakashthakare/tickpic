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

    // yes, no, maybe (not sure or skip)
    // keyboard shortcuts (Yes - Y, No - N, S - Skip)
    // highligh current selection if image render again

    public SelectionManager(Sidebar sidebar) {
        HBox hbox = new HBox();
        Button yes = new Button("Yes");
        Button no = new Button("No");

        yes.setOnAction(e -> sidebar.update("Y"));
        no.setOnAction(e -> sidebar.update("N"));

        hbox.getChildren().addAll(yes, no);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.BASELINE_CENTER);
        hbox.prefWidthProperty().bind(widthProperty());

        getChildren().add(hbox);

        setBackground(new Background(new BackgroundFill(
            Color.TEAL,
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));
    }
}
