package com.app.javafxapp.ui;

import com.app.javafxapp.db.DataManager;
import com.app.javafxapp.domain.Selection;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
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
    private SelectionManager selectionManager;

    public int selected;

    private int total;

    private List<String> filePaths;

    private List<Image> images;

    private List<Button> selectionLabels;

    private List<Pane> imageViews;

    private VBox box;
    private String selectedDirectory;

    public Sidebar(ImageRenderer renderer, SelectionManager selectionManager) {
        this.renderer = renderer;
        renderer.setVisible(false);
        renderer.addRightArrowClickListener(e -> rightArrowClicked());
        renderer.addLeftArrowClickListener(e -> leftArrowClicked());

        this.selectionManager = selectionManager;
        selectionManager.addNoSelectionListener(e -> setSelected(false));
        selectionManager.addYesSelectionListener(e -> setSelected(true));

        images = new ArrayList<>();
        imageViews = new ArrayList<>();
        selectionLabels = new ArrayList<>();
        filePaths = new ArrayList<>();
        box = new VBox();
        box.setSpacing(10);

        setContent(box);
        setVisible(false);
    }

    void loadImages(String path) {
        this.selectedDirectory = path;
        List<Selection> fetched = DataManager.fetch(selectedDirectory);
        Map<String, Boolean> fileSelectionMap =
            fetched.stream().collect(Collectors.toMap(Selection::getFile, Selection::getSelected));

        clear();

        new Thread(() -> {
            File directory = new File(selectedDirectory);
            List<File> files = new ArrayList<>();
            if(!directory.exists()) {
                Platform.runLater(this::showAlert);
                return;
            } else {
                File[] filesArr = directory.listFiles();
                if (filesArr == null || filesArr.length == 0) {
                    Platform.runLater(this::showAlert);
                    return;
                } else {
                    files.addAll(Arrays.asList(filesArr));
                }
            }

            Platform.runLater(() -> {
                int i = 0;
                for (File file : files) {
                    try {
                        String mime = Files.probeContentType(file.toPath());
                        if(mime != null && !mime.startsWith("image")) continue;
                        total++;
                        Image image = new Image(file.toURI().toURL().toString(), 100, 100, false, false);
                        ImageView imageView = new ImageView(image);
                        imageView.setUserData(i);
                        imageView.setPreserveRatio(true);

                        Button label = new Button();
                        Pane imagePane = new Pane();
                        imagePane.getChildren().add(imageView);
                        imagePane.getChildren().add(label);

                        String fileName = getFileName(file.getAbsolutePath());
                        filePaths.add(fileName);

                        if(fileSelectionMap.get(fileName) != null) {
                            updateSelectionLabel(label, fileSelectionMap.get(fileName));
                        } else {
                            label.setBackground(new Background(new BackgroundFill(
                                Color.TRANSPARENT,
                                CornerRadii.EMPTY,
                                Insets.EMPTY
                            )));
                        }

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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    this.setVisible(true);
                    renderer.setVisible(true);
                    selectionManager.setVisible(true);
                }
                updateFocus(true);
            });
        }).start();
    }

    private void clear() {
        box.getChildren().clear();
        images.clear();
        imageViews.clear();
        selectionLabels.clear();
        filePaths.clear();
        total = selected = 0;
    }

    private void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText("Failed to load files.");
        alert.setContentText("Please check the folder path and ensure it's valid and contains one or more images in it.");
        alert.showAndWait();
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
        Button label = selectionLabels.get(selected);
        updateSelectionLabel(label, isSelected);
        DataManager.save(selectedDirectory, getFileName(filePaths.get(selected)), isSelected);
        rightArrowClicked();
    }

    private void updateSelectionLabel(Button label, boolean isSelected) {
        String s = isSelected ? "Y" : "N";
        label.setText(s);
        label.toFront();
        label.setBackground(new Background(new BackgroundFill(
            isSelected ? Color.rgb(168, 213, 186) : Color.rgb(242, 139, 130),
            new CornerRadii(25),
            Insets.EMPTY
        )));
    }

    private static String getFileName(String url) {
        String[] segments = url.split("/");
        return segments[segments.length - 1];
    }
}
