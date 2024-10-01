package com.app.javafxapp.ui;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Side;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Sidebar  extends ScrollPane {

    // thumbnails
    // open image on click
    // jump to image
    // background color change as per selection
    // scroll


    public Sidebar() {
        List<ImageView> imageViews = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            Image image = new  Image("https://fastly.picsum.photos/id/870/536/354.jpg?blur=2&grayscale&hmac=A5T7lnprlMMlQ18KQcVMi3b7Bwa1Qq5YJFp8LSudZ84", 100, 100, false, false);
            ImageView imageView = new ImageView(image);
            imageViews.add(imageView);
        }
        VBox box = new VBox();
        box.setSpacing(10.0);
        box.getChildren().addAll(imageViews);

        setContent(box);
    }
}
