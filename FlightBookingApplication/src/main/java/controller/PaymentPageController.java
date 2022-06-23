package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PaymentPageController implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnPay;

    @FXML
    private VBox creditCardList;

    @FXML
    private HBox parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parent.getStylesheets().add(getClass().getResource("/style/PaymentPage.css").toExternalForm());

        for (int i = 0; i < 3; i++) {
            try {
                AnchorPane creditCard = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SearchPage/CreditCard.fxml")));
                creditCardList.getChildren().add(creditCard);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void payNow(ActionEvent event) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource("/view/SearchPage/TicketPage.fxml"));
            VBox.setVgrow(page, Priority.ALWAYS);

            StackPane content = (StackPane) parent.getScene().lookup("#content");
            content.getChildren().clear();
            content.getChildren().add(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void goBack(ActionEvent event) {
        StackPane content = (StackPane) parent.getScene().lookup("#content");
        int recentChild = content.getChildren().size() - 1;
        content.getChildren().remove(recentChild);
    }
}
