package controller;

import data.ReservationDao;
import data.SeatDao;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Account;
import models.Flight;
import models.Seat;
import view.Palette;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SeatMapController implements Initializable {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnCancel;

    @FXML
    private ToggleButton btnFavorite;

    @FXML
    private HBox confirmationWindow;

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
    private Label lblBusinessPrice;

    @FXML
    private Label lblDepAirport;

    @FXML
    private Label lblDepCity;

    @FXML
    private Label lblDepDate;

    @FXML
    private Label lblDepTime;

    @FXML
    private Label lblEconomyPrice;

    @FXML
    private Label lblFirstPrice;

    @FXML
    private Label lblFirstname;

    @FXML
    private Label lblFromCode;

    @FXML
    private Label lblSeat;

    @FXML
    private Label lblSelectedSeat;

    @FXML
    private Label lblToCode;

    @FXML
    private StackPane parent;
    @FXML
    private GridPane seatMap;

    private final ToggleGroup seatGroup = new ToggleGroup();
    private Flight flight;
    private Seat selectedSeat;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parent.getStylesheets().add(getClass().getResource("/style/SeatMap.css").toExternalForm());

        btnCancel.setTooltip(new Tooltip("Cancel Reservation"));

        seatGroup.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
            //when a seat is selected set the confirmationWindow visibility to true, otherwise set it to false
            if (newValue != null) {
                confirmationWindow.setVisible(true);
                selectedSeat = (Seat) seatGroup.getSelectedToggle();
                lblSeat.setText(selectedSeat.getColumn() + selectedSeat.getRow());
            }
            else {
                confirmationWindow.setVisible(false);
                selectedSeat = null;
            }
        }));
    }

    public void setData(Flight flight) {
        this.flight = flight;

        fillSeatMap();

        lblDepAirport.setText(flight.getDepAirport().getName());
        lblDepCity.setText(flight.getDepAirport().getCity() + " - " + flight.getDepAirport().getCountry());
        lblDepDate.setText(flight.getDepDatetime().toLocalDate().toString());
        lblDepTime.setText(flight.getDepDatetime().toLocalTime().toString());

        lblAirline.setText(flight.getAirline().getName());

        lblArrAirport.setText(flight.getArrAirport().getName());
        lblArrCity.setText(flight.getArrAirport().getCity() + " - " + flight.getArrAirport().getCountry());
        lblArrDate.setText(flight.getArrDatetime().toLocalDate().toString());
        lblArrTime.setText(flight.getArrDatetime().toLocalTime().toString());

        lblSelectedSeat.setText((selectedSeat == null) ? "" : selectedSeat.getColumn() + selectedSeat.getRow());

        lblFirstPrice.setText(flight.getFirstPriceFormatted());
        lblBusinessPrice.setText(flight.getBusinessPriceFormatted());
        lblEconomyPrice.setText(flight.getEconomyPriceFormatted());

        lblFirstname.setText(Account.getCurrentUser().getPassenger().getFirstname());
        Account.getCurrentUser().getPassenger().firstnameProperty().addListener((observable, oldValue, newValue) -> {
            lblFirstname.setText(newValue);
        });
        String depIATA = flight.getDepAirport().getIATA();
        String depICAO = flight.getDepAirport().getICAO();
        lblFromCode.setText((depIATA != null) ? depIATA : depICAO);
        String arrIATA = flight.getArrAirport().getIATA();
        String arrICAO = flight.getArrAirport().getICAO();
        lblToCode.setText((arrIATA != null) ? arrIATA : arrICAO);

        if(Account.getCurrentUser().hasReservation(flight)) {
            btnCancel.setVisible(true);
        }

        btnFavorite.setSelected(flight.isFavorite());
        if (flight.isFavorite()) {
            btnFavorite.setOnAction(event -> flight.removeFavorite());
        }
        else {
            btnFavorite.setOnAction(event -> flight.addFavorite());
        }

        flight.getFavoriteProperty().addListener((observable, oldValue, newValue) -> {
            btnFavorite.setSelected(newValue);
        });

        btnFavorite.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                Platform.runLater(() -> btnFavorite.setOnAction(event -> flight.removeFavorite()));
            }
            else {
                Platform.runLater(() -> btnFavorite.setOnAction(event -> flight.addFavorite()));
            }
        });
    }

    @FXML
    private void cancelSelected() {
        seatGroup.selectToggle(null);
    }

    @FXML
    private void confirmSelected() {
        try {
            FXMLLoader paymentLoader = new FXMLLoader(getClass().getResource("/view/SearchPage/PaymentPage.fxml"));
            Parent page = paymentLoader.load();
            VBox.setVgrow(page, Priority.ALWAYS);

            PaymentPageController paymentController = paymentLoader.getController();
            paymentController.setData(flight, selectedSeat);

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
        FlightCardController cardController = (FlightCardController) parent.getUserData();
        cardController.changeActionButtons();
    }

    @FXML
    void cancelReservation(ActionEvent event) {
        parent.getScene().lookup("#overlay-layer").setDisable(false);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you really want to cancel your reservation ?");
        alert.setHeaderText("Confirm the action");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("YES");
        ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("NO");

        DialogPane dialogPane = alert.getDialogPane();

        Palette.getDefaultPalette().usePalette(dialogPane.getScene());
        dialogPane.getStylesheets().add(getClass().getResource("/style/Application.css").toExternalForm());

        Stage alertWindow = (Stage) dialogPane.getScene().getWindow();
        alertWindow.initStyle(StageStyle.TRANSPARENT);
        dialogPane.getScene().setFill(Color.TRANSPARENT);
        alertWindow.initOwner(parent.getScene().getWindow());

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK){
            ReservationDao reservationDao = new ReservationDao();
            reservationDao.delete(Account.getCurrentUser().getReservation(flight).getId());

            alert.close();
            parent.getScene().lookup("#overlay-layer").setDisable(true);
            goBack(new ActionEvent());
        } else {
            alert.close();
            parent.getScene().lookup("#overlay-layer").setDisable(true);
        }
    }

    private void fillSeatMap() {
        SeatDao seatDao = new SeatDao();
        ArrayList<Seat> seatList = new ArrayList<>(seatDao.readAll());

        // add a row contains Columns numbering
        char ref = 'A';
        for (int col = 0; col <= 6; col++) {
            if (col == 3) {continue;}
            Label lbl = new Label(Character.toString(ref++));
            lbl.setMinSize(40, 40);
            lbl.setAlignment(Pos.CENTER);
            seatMap.add(lbl, col, 0);
        }

        int id = 0;
        final int nbColumn = 6;
        int nbRows = seatList.size() / nbColumn;
        int seatRow = 1;

        for (int row = 1; row <= nbRows; row++) {
            for (int col = 0; col <= nbColumn; col++) {
                // add a column contains row numbering
                if (col == 3) {
                    Label lbl = new Label(seatRow++ + "");
                    lbl.setMinSize(40, 40);
                    lbl.setAlignment(Pos.CENTER);
                    seatMap.add(lbl, col, row);
                    continue;
                }

                // add the exit labels
                if (row == 8 || row == 12) {
                    Label leftExit = new Label("◀ EXIT");
                    Label rightExit = new Label("EXIT ▶");
                    leftExit.setMinSize(40, 40);
                    rightExit.setMinSize(40, 40);
                    rightExit.setAlignment(Pos.BASELINE_RIGHT);
                    seatMap.add(leftExit, 0, row);
                    seatMap.add(rightExit, 6, row);

                    nbRows++;
                    break;
                }

                Seat seat = seatList.get(id++);
                seatGroup.getToggles().add(seat);

                //disable the reserved seats
                if (seat.isReservedBy(Account.getCurrentUser(), flight)) {
                    seatGroup.selectToggle(seat);
                    selectedSeat = seat;
                }
                else if (seat.isReserved(flight)) {
                    seat.getStyleClass().add("UnavailableSeatIcon");
                    seat.setDisable(true);
                }

                //add the seat to the setMap
                seatMap.add(seat, col, row);
            }
        }
    }
}
