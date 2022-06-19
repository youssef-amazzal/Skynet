package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PaymentPageController implements Initializable {

    @FXML
    private VBox creditCardList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 3; i++) {
            try {
                AnchorPane creditCard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SearchPage/CreditCard.fxml")));
                creditCardList.getChildren().add(creditCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
