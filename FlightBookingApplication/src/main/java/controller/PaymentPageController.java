package controller;

import data.CreditCardDao;
import data.ReservationDao;
import javafx.beans.property.SimpleListProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.*;
import view.Palette;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PaymentPageController implements Initializable {

    @FXML
    private VBox creditCardList;

    @FXML
    private Label lblAirline;

    @FXML
    private Label lblArrAirport;

    @FXML
    private Label lblArrCity;

    @FXML
    private Label lblArrDate;

    @FXML
    private Label lblArrTime;

    @FXML
    private Label lblClassPrice;

    @FXML
    private Label lblClassType;

    @FXML
    private Label lblDepAirport;

    @FXML
    private Label lblDepCity;

    @FXML
    private Label lblDepDate;

    @FXML
    private Label lblDepTime;

    @FXML
    private Label lblLuggagesPrice;

    @FXML
    private Label lblTotalPrice;

    @FXML
    private Label lblWeightPrice;

    @FXML
    private HBox parent;

    @FXML
    private Spinner<Integer> spinnerLuggage;

    @FXML
    private Spinner<Double> spinnerWeight;
    private Flight flight;
    private Seat selectedSeat;
    private double classPrice;
    private double luggagePrice;
    private double weightPrice;
    private final ToggleGroup cardGroup = new ToggleGroup();

    private SimpleListProperty<CreditCard> cardList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parent.getStylesheets().add(getClass().getResource("/style/PaymentPage.css").toExternalForm());

        CreditCardDao creditCardDao = new CreditCardDao();
        cardList = new SimpleListProperty<>(creditCardDao.getCardList());
        loadCards();

        cardList.sizeProperty().addListener(observable -> loadCards());

    }

    private void alwaysOneSelected() {
        cardGroup.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
            if (newVal == null)
                oldVal.setSelected(true);
        });
    }

    private void loadCards() {

        creditCardList.getChildren().clear();

        if (!cardList.isEmpty()) {
            for (CreditCard card : cardList) {
                try {
                    FXMLLoader cardLoader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/view/SearchPage/CreditCard.fxml")));
                    ToggleButton creditCard = cardLoader.load();

                    CreditCardController cardController = cardLoader.getController();
                    cardController.setData(card);

                    creditCardList.getChildren().add(creditCard);
                    cardGroup.getToggles().add(creditCard);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            cardGroup.selectToggle(cardGroup.getToggles().get(0));
            alwaysOneSelected();
        }
    }

    @FXML
    void payNow(ActionEvent event) {
        ReservationDao reservationDao = new ReservationDao();
        if (cardGroup.getToggles().isEmpty()) {
            parent.getScene().lookup("#overlay-layer").setDisable(false);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Warning");
            alert.setContentText("Please add a credit card to continue");

            DialogPane dialogPane = alert.getDialogPane();

            Palette.getDefaultPalette().usePalette(dialogPane.getScene());
            dialogPane.getStylesheets().add(getClass().getResource("/style/Application.css").toExternalForm());

            Stage alertWindow = (Stage) dialogPane.getScene().getWindow();
            alertWindow.initStyle(StageStyle.TRANSPARENT);
            dialogPane.getScene().setFill(Color.TRANSPARENT);
            alertWindow.initOwner(parent.getScene().getWindow());

            alert.showAndWait();
            parent.getScene().lookup("#overlay-layer").setDisable(true);
            return;
        }
        Reservation reservation = new Reservation(flight, Account.getCurrentUser().getId(), selectedSeat, spinnerLuggage.getValue(), spinnerWeight.getValue());

        if (Account.getCurrentUser().hasReservation(flight)) {
            reservationDao.update(Account.getCurrentUser().getReservation(flight).getId(), reservation);
        }
        else {
            reservationDao.create(reservation);
        }

        try {
            FXMLLoader ticketLoader = new FXMLLoader(getClass().getResource("/view/SearchPage/TicketPage.fxml"));
            Parent page = ticketLoader.load();
            VBox.setVgrow(page, Priority.ALWAYS);

            TicketPageController ticketController = ticketLoader.getController();
            ticketController.setData(reservation);

            ApplicationController.navBarController.pushPage(page);

            StackPane content = (StackPane) parent.getScene().lookup("#content");
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
        ApplicationController.navBarController.popPage();
    }

    @FXML
    void addCrad() {
        AccountPageController controller = ApplicationController.navBarController.openAccount(new ActionEvent());
        controller.openCardsTab(new ActionEvent());
    }

    public void setData(Flight flight, Seat selectedSeat) {
        this.flight = flight;
        this.selectedSeat = selectedSeat;
        lblDepAirport.setText(flight.getDepAirport().getName());
        lblDepCity.setText(flight.getDepAirport().getCity() + " - " + flight.getDepAirport().getCountry());
        lblDepDate.setText(flight.getDepDatetime().toLocalDate().toString());
        lblDepTime.setText(flight.getDepDatetime().toLocalTime().toString());

        lblAirline.setText(flight.getAirline().getName());

        lblArrAirport.setText(flight.getArrAirport().getName());
        lblArrCity.setText(flight.getArrAirport().getCity() + " - " + flight.getArrAirport().getCountry());
        lblArrDate.setText(flight.getArrDatetime().toLocalDate().toString());
        lblArrTime.setText(flight.getArrDatetime().toLocalTime().toString());

        lblClassType.setText(selectedSeat.getType() + " class");
        switch (selectedSeat.getType().toLowerCase()) {
            case "first" -> {
                classPrice = flight.getFirstPrice();
                lblClassPrice.setText(flight.getFirstPriceFormatted());
            }
            case "business" -> {
                classPrice = flight.getBusinessPrice();
                lblClassPrice.setText(flight.getBusinessPriceFormatted());
            }
            case "economy" -> {
                classPrice = flight.getEconomyPrice();
                lblClassPrice.setText(flight.getEconomyPriceFormatted());
            }
        }

        {
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5);
            spinnerLuggage.setValueFactory(valueFactory);

            lblLuggagesPrice.setText(flight.getLuggagePrice() * spinnerLuggage.getValue() + "$");
            spinnerLuggage.valueProperty().addListener((observable, oldValue, newValue) -> {
                luggagePrice = flight.getLuggagePrice() * newValue;
                lblLuggagesPrice.setText(luggagePrice + "$");

                lblTotalPrice.setText(calculateTotalPrice() + "$");
            });
        }
        {
            SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100);
            spinnerWeight.setValueFactory(valueFactory);

            lblWeightPrice.setText(flight.getWeightPrice() * spinnerWeight.getValue() + "$");
            spinnerWeight.valueProperty().addListener((observable, oldValue, newValue) -> {
                weightPrice = flight.getWeightPrice() * newValue;
                lblWeightPrice.setText(weightPrice + "$");

                lblTotalPrice.setText(calculateTotalPrice() + "$");
            });
        }

        if (Account.getCurrentUser().hasReservation(flight)) {
            Reservation reservation = Account.getCurrentUser().getReservation(flight);
            spinnerWeight.getValueFactory().setValue(reservation.getWeight());
            spinnerLuggage.getValueFactory().setValue(reservation.getNbrLuggages());
        }

        lblTotalPrice.setText(calculateTotalPrice() + "$");
    }

    private double calculateTotalPrice() {
        return classPrice + luggagePrice + weightPrice;
    }
}
