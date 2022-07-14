package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import models.Account;
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
        String firstname = bankCard.getCardHolder().getPassenger().getFirstname();
        String lastname = bankCard.getCardHolder().getPassenger().getLastname();
        lblCardHolder.setText(firstname + " " + lastname);
        lblCardNumber.setText(String.valueOf(bankCard.getCardNumberFormatted()));
        int expYear = bankCard.getExpirationDate().getYear();
        int expMonth = bankCard.getExpirationDate().getMonthValue();
        lblExpirationDate.setText(expMonth + "/" + expYear);
    }
}
