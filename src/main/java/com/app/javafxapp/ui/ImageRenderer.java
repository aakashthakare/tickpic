package com.app.javafxapp.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ImageRenderer extends Pane {

    // image
    // left-right navigation
    // keyboard shortcut for navigation

    private ImageView imageView;

    public ImageRenderer() {
        this.imageView = new ImageView();
        getChildren().add(imageView);
    }

    public void load(String path) {
        Image image = new Image("https://fastly.picsum.photos/id/870/536/354.jpg?blur=2&grayscale&hmac=A5T7lnprlMMlQ18KQcVMi3b7Bwa1Qq5YJFp8LSudZ84");
        imageView.setImage(image);
    }
}
