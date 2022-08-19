module FlightBookingApplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires javafx.swing;

    opens application;
    opens controller;
    opens models;
}