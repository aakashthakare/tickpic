module com.app.javafxapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.app.javafxapp to javafx.fxml;
    exports com.app.javafxapp;
}