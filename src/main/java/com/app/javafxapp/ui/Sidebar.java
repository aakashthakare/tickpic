package com.app.javafxapp.ui;

import com.app.javafxapp.db.DataManager;
import com.app.javafxapp.db.Selection;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    private List<Image> images;

    private List<Button> selectionLabels;

    private List<Pane> imageViews;

    private VBox box;
    private String selectedDirectory;

    public Sidebar(ImageRenderer renderer) {
        this.renderer = renderer;
        images = new ArrayList<>();
        imageViews = new ArrayList<>();
        selectionLabels = new ArrayList<>();
        box = new VBox();
        setContent(box);
        renderer.setVisible(false);
        setVisible(false);
    }

    void loadImages(String path) {
        this.selectedDirectory = path;
        List<Selection> fetched = DataManager.fetch(selectedDirectory);
        Map<String, Boolean> fileSelectionMap =
            fetched.stream().collect(Collectors.toMap(s -> s.getFile(), s -> s.getSelected()));

        box.getChildren().clear();
        images.clear();
        imageViews.clear();
        selectionLabels.clear();
        total = selected = 0;

        new Thread(() -> {
            File directory = new File(path);
            List<File> files = new ArrayList<>();
            if(!directory.exists()) {
                showAlert();
                return;
            } else {
                File[] filesArr = directory.listFiles();
                if (filesArr == null) {
                    showAlert();
                    return;
                } else {
                    for (File file : filesArr) {
                        files.add(file);
                    }
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

                        Button label = new Button("?");
                        Pane imagePane = new Pane();
                        imagePane.getChildren().add(imageView);
                        imagePane.getChildren().add(label);

                        String fileName = getFileName(file.getAbsolutePath());
                        if(fileSelectionMap.get(fileName) != null) {
                            updateSelectionLabel(label, fileSelectionMap.get(fileName));
                        } else {
                            label.setBackground(new Background(new BackgroundFill(
                                Color.DARKGRAY,
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
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    this.setVisible(true);
                    renderer.setVisible(true);
                    selectionManager.setVisible(true);
                }
                updateFocus(true);
            });
        }).start();
    }

    private static void showAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText("Failed to load files.");
        alert.setContentText("Please check the folder path and ensure it's valid.");
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
        String s = isSelected ? "Y" : "N";
        Button label = selectionLabels.get(selected);
        label.setText(s);
        label.toFront();
        label.setBackground(new Background(new BackgroundFill(
            isSelected ? Color.LIGHTGREEN : Color.RED,
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));
        DataManager.save(selectedDirectory, getFileName(images.get(selected).getUrl()), isSelected);
        rightArrowClicked();
    }

    private void updateSelectionLabel(Button label, boolean isSelected) {
        String s = isSelected ? "Y" : "N";
        label.setText(s);
        label.toFront();
        label.setBackground(new Background(new BackgroundFill(
            isSelected ? Color.LIGHTGREEN : Color.RED,
            CornerRadii.EMPTY,
            Insets.EMPTY
        )));
    }

    public void setSelectionManager(SelectionManager selectionManager) {
        this.selectionManager = selectionManager;
    }

    private static String getFileName(String url) {
        String[] segments = url.split("/");
        return segments[segments.length - 1];
    }
}
