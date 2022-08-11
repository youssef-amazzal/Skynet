package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MyCardsController implements Initializable {

    @FXML
    private Button actionButton;

    @FXML
    private StackPane cardStack;

    @FXML
    private PasswordField inputCVV;

    @FXML
    private TextField inputCardHolder;

    @FXML
    private TextField inputCardNumber;

    @FXML
    private TextField inputExpiryDate;

    @FXML
    private Button nextButton;

    @FXML
    private Button previousButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    void nextCard(ActionEvent event) {

    }

    @FXML
    void previousCard(ActionEvent event) {

    }
}
