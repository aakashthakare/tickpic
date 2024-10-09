package com.app.javafxapp.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Sidebar  extends ScrollPane {
    private final ImageRenderer renderer;

    public int selected;

    private int total;

    private List<Image> images;

    private List<Button> selectionLabels;

    private List<Pane> imageViews;

    private VBox box;

    public Sidebar(ImageRenderer renderer) {
        this.renderer = renderer;
        images = new ArrayList<>();
        imageViews = new ArrayList<>();
        selectionLabels = new ArrayList<>();
        box = new VBox();
        box.setPadding(new Insets(5));
        box.setSpacing(5);
        setContent(box);
        loadImages();
    }

    private void loadImages() {
        new Thread(() -> {
            File directory = new File("/Users/akash/Pictures/Screenshots");
            File[] files = directory.listFiles();
            total = files.length;

            Platform.runLater(() -> {
                int i = 0;
                for (File file : files) {
                    try {
                        Image image = new Image(file.toURI().toURL().toString(), 100, 100, false, false);
                        ImageView imageView = new ImageView(image);
                        imageView.setUserData(i);
                        imageView.setPreserveRatio(true);

                        Button label = new Button("?");
                        Pane imagePane = new Pane();
                        imagePane.getChildren().add(imageView);
                        imagePane.getChildren().add(label);
                        label.setBackground(new Background(new BackgroundFill(
                            Color.DARKGRAY,
                            CornerRadii.EMPTY,
                            Insets.EMPTY
                        )));

                        images.add(image);
                        imageViews.add(imagePane);
                        selectionLabels.add(label);

                        imageView.setOnMouseClicked(e -> {
                            updateFocus(false);
                            selected = (Integer) imageView.getUserData();
                            updateFocus(true);
                        });
                        i++;
                        box.getChildren().add(imagePane);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
                updateFocus(true);
            });
        }).start();
    }

    public void move(KeyEvent e) {
        switch (e.getCode()) {
            case Y -> setSelected(true);
            case N -> setSelected(false);
            case LEFT -> leftArrowClicked();
            case RIGHT -> rightArrowClicked();
        }
    }

    public void updateFocus(boolean isFocus) {
        renderer.load(images.get(selected).getUrl());
        imageViews.get(selected).setBackground(new Background(new BackgroundFill(
            isFocus ? Color.LIGHTGREEN : Color.GRAY,
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));
    }

    public void leftArrowClicked() {
        if(selected > 0) {
            updateFocus(false);
            selected--;
            updateFocus(true);
        }
    }

    public void rightArrowClicked() {
        if(selected < total - 1) {
            updateFocus(false);
            selected++;
            updateFocus(true);
        }
    }

    public void setSelected(boolean isSelected) {
        String s = isSelected ? "Y" : "N";
        Button label = selectionLabels.get(selected);
        label.setText(s);
        label.toFront();
        label.setBackground(new Background(new BackgroundFill(
            isSelected ? Color.LIGHTGREEN : Color.RED,
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));
        rightArrowClicked();
    }
}
