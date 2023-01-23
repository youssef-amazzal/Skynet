package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AccountPageController implements Initializable {

    @FXML
    private HBox parent;

    @FXML
    private VBox tabContent;

    @FXML
    private ToggleGroup tabs;

    @FXML
    private ToggleButton toggleCards;

    @FXML
    private ToggleButton togglePassword;

    @FXML
    private ToggleButton togglePersonal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parent.getStylesheets().add(getClass().getResource("/style/AccountPage.css").toExternalForm());

        try {
            tabContent.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/accountPage/PersonalInformation.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openCardsTab(ActionEvent event) {
        loadTab("/view/accountPage/MyCards.fxml");
        toggleCards.setSelected(true);
    }

    @FXML
    void openPasswordTab(ActionEvent event) {
        loadTab("/view/accountPage/Password-Security.fxml");
        togglePassword.setSelected(true);
    }

    @FXML
    void openPersonalTab(ActionEvent event) {
        loadTab("/view/accountPage/PersonalInformation.fxml");
        togglePersonal.setSelected(true);
    }

    private void loadTab(String path) {
        try {
            Parent tab = FXMLLoader.load(getClass().getResource(path));
            VBox.setVgrow(tab, Priority.ALWAYS);

            tabContent.getChildren().clear();
            tabContent.getChildren().add(tab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
