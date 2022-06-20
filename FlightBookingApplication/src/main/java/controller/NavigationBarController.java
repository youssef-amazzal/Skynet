package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NavigationBarController implements Initializable {

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navigationBar.getStylesheets().add(getClass().getResource("/style/NavigationBar.css").toExternalForm());
        VBox.setVgrow(navigationBar, Priority.ALWAYS);
        alwaysOneSelected();
    }

    private void alwaysOneSelected() {
        navigationBarGroup.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
            if (newVal == null)
                oldVal.setSelected(true);
        });
    }

    private void loadPage (String path) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource(path));
            VBox.setVgrow(page, Priority.ALWAYS);

            VBox content = (VBox) navigationBar.getScene().lookup("#content");
            content.getChildren().clear();
            content.getChildren().add(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Logout(ActionEvent event) {

    }

    @FXML
    void openAccount(ActionEvent event) {
        loadPage("/view/accountPage/AccountPage.fxml");
    }

    @FXML
    void openHome(ActionEvent event) {
    }

    @FXML
    void openSearch(ActionEvent event) {
        loadPage("/view/SearchPage/SearchPage.fxml");
    }

    @FXML
    void openSettings(ActionEvent event) {

    }
}