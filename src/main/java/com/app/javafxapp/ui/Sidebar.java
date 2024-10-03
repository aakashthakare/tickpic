package com.app.javafxapp.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Sidebar  extends ScrollPane {
    private final ImageRenderer renderer;

    // thumbnails
    // open image on click
    // jump to image
    // background color change as per selection
    // scroll

    public int selected;

    private int total;

    private List<Image> images;

    private List<Pane> imageViews;

    public Sidebar(ImageRenderer renderer) {
        this.renderer = renderer;
        images = new ArrayList<>();
        imageViews = new ArrayList<>();

        URL url = this.getClass().getClassLoader().getResource("images");
        File directory = null;
        try {
            directory = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        File[] files = directory.listFiles();
        total = files.length;
        int i = 0;

        for (File file : files) {
            try {
                Image image = new Image(file.toURI().toURL().toString(), 100, 100, false, false);
                ImageView imageView = new ImageView(image);
                imageView.setUserData(i);

                Pane imagePane = new Pane();
                imagePane.getChildren().add(imageView);
                imagePane.setPadding(new Insets(1));
                imagePane.setBackground(new Background(new BackgroundFill(
                    Color.GRAY,
                    CornerRadii.EMPTY,
                    Insets.EMPTY
                )));
                images.add(image);
                imageViews.add(imagePane);

                if(i == 0) {
                    selected = i;
                    selectImage();
                }

                imageView.setOnMouseClicked(e -> {
                    deselectImage();
                    selected = (Integer) imageView.getUserData();
                    selectImage();
                });
                i++;
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        VBox box = new VBox();
        box.setPadding(new Insets(5));
        box.setSpacing(5);
        box.getChildren().addAll(imageViews);
        setContent(box);
    }

    public void move(KeyEvent e) {
        if(KeyCode.LEFT == e.getCode()) {
            leftArrowClicked();
        }

        if(KeyCode.RIGHT == e.getCode()) {
            rightArrowClicked();
        }
    }

    public void deselectImage() {
        imageViews.get(selected).setBackground(new Background(new BackgroundFill(
            Color.GRAY,
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));
    }

    public void selectImage() {
        renderer.load(images.get(selected).getUrl());
        imageViews.get(selected).setBackground(new Background(new BackgroundFill(
            Color.LIGHTGREEN,
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));
    }

    public void leftArrowClicked() {
        if(selected > 0) {
            deselectImage();
            selected--;
            selectImage();
        }
    }

    public void rightArrowClicked() {
        if(selected < total - 1) {
            deselectImage();
            selected++;
            selectImage();
        }
    }
}
