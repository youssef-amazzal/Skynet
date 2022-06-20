package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AccountPageController implements Initializable {

    @FXML
    private HBox parent;

    @FXML
    private VBox page;

    @FXML
    private ToggleGroup tabs;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parent.getStylesheets().add(getClass().getResource("/style/AccountPage.css").toExternalForm());

        try {
            page.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/accountPage/PersonalInformation.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void alwaysOneSelected() {
        tabs.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
            if (newVal == null)
                oldVal.setSelected(true);
        });
    }
}
