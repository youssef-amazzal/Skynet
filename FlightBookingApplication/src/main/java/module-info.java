module $MODULE_NAME$ {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires fr.brouillard.oss.cssfx;

    opens application;
    opens controller;
}