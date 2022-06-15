package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class NavigationBarController {

    @FXML
    private ToggleButton accounBtn;

    @FXML
    private Label appLogo;

    @FXML
    private ToggleButton homeBtn;

    @FXML
    private ToggleButton logoutBtn;

    @FXML
    private VBox navigationBar;

    @FXML
    private ToggleGroup navigationBarGroup;

    @FXML
    private ToggleButton searchBtn;

    @FXML
    private ToggleButton settingsBtn;

}