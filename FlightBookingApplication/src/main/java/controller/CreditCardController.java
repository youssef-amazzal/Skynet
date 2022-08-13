package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import models.BankCard;

import java.net.URL;
import java.util.ResourceBundle;

public class CreditCardController implements Initializable {

    @FXML
    private Label lblCardHolder;

    @FXML
    private Label lblCardNumber;

    @FXML
    private Label lblExpirationDate;

    @FXML
    private ToggleButton parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setData(BankCard bankCard) {
        lblCardHolder.textProperty().bind(bankCard.cardHolderProperty());
        lblCardNumber.setText(bankCard.getCardNumberFormatted());
        bankCard.cardNumberProperty().addListener((observable, oldValue, newValue) -> {
            lblCardNumber.setText(bankCard.getCardNumberFormatted());
        });
        lblExpirationDate.textProperty().bind(bankCard.expirationDateProperty());
    }
}
