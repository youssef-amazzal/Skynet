package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.CreditCard;

import java.net.URL;
import java.util.ResourceBundle;

public class CreditCardController implements Initializable {

    @FXML
    private Label lblCardHolder;

    @FXML
    private Label lblCardNumber;

    @FXML
    private Label lblExpirationDate;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setData(CreditCard creditCard) {
        lblCardHolder.textProperty().bind(creditCard.cardHolderProperty());
        lblCardNumber.setText(creditCard.getCardNumberFormatted());
        creditCard.cardNumberProperty().addListener((observable, oldValue, newValue) -> {
            lblCardNumber.setText(creditCard.getCardNumberFormatted());
        });
        lblExpirationDate.textProperty().bind(creditCard.expirationDateProperty());
    }
}
