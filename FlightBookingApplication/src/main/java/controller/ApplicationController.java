package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ApplicationController implements Initializable {

    @FXML
    private VBox content;

    @FXML
    private VBox navBarContainer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader navBarLoader = new FXMLLoader(getClass().getResource("/view/NavigationBar.fxml"));
        try {
            navBarContainer.getChildren().add(navBarLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
