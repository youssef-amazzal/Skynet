module FlightBookingApplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires fr.brouillard.oss.cssfx;
    requires java.sql;
    requires org.controlsfx.controls;

    opens application;
    opens controller;
}