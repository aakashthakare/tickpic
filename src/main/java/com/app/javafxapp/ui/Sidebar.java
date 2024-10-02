package com.app.javafxapp.ui;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventType;
import javafx.geometry.Side;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Sidebar  extends ScrollPane {
    private final ImageRenderer renderer;

    // thumbnails
    // open image on click
    // jump to image
    // background color change as per selection
    // scroll


    public Sidebar(ImageRenderer renderer) {
        this.renderer = renderer;
        List<ImageView> imageViews = new ArrayList<>();

        URL url = this.getClass().getClassLoader().getResource("images");
        File directory = null;
        try {
            directory = new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        File[] files = directory.listFiles();

        int i = 0;

        for (File file : files) {
            try {
                Image image = new Image(file.toURI().toURL().toString(), 100, 100, false, false);
                ImageView imageView = new ImageView(image);
                imageViews.add(imageView);
                imageView.setOnMouseClicked(e -> {
                    renderer.load(image.getUrl());
                });
                if(i == 0) {
                    renderer.load(image.getUrl());
                    i++;
                }
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        VBox box = new VBox();
        box.setSpacing(10.0);
        box.getChildren().addAll(imageViews);

        setContent(box);
    }
}
