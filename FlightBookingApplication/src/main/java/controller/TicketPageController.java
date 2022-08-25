package controller;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import models.*;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class TicketPageController implements Initializable {

    @FXML
    private ImageView imgAirlineLogo;

    @FXML
    private ImageView imgAppLogo;

    @FXML
    private Label lblArrAirport;

    @FXML
    private Label lblArrCity;

    @FXML
    private Label lblArrDateTime;

    @FXML
    private Label lblClassType;

    @FXML
    private Label lblDepAirport;

    @FXML
    private Label lblDepCity;

    @FXML
    private Label lblDepDateTime;

    @FXML
    private Label lblFlightID;

    @FXML
    private Label lblSelectedSeat;

    @FXML
    private Label lblPassengerName;

    @FXML
    private HBox parent;

    @FXML
    private VBox ticket;
    private Reservation reservation;

    @FXML
    void downloadTicket(ActionEvent event) {
        String depCity = reservation.getFlight().getDepAirport().getCity();
        String arrCity = reservation.getFlight().getArrAirport().getCity();
        try {
            WritableImage writableImage = ticket.snapshot(new SnapshotParameters(), null);

            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("Ticket_From_"+depCity+"_To_"+arrCity);
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PNG IMAGE","*.png")
            );

            File selectedFile = fileChooser.showSaveDialog(parent.getScene().getWindow());

            if (selectedFile != null) {
                File file = new File(selectedFile.getAbsolutePath());
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", file);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void closePage(ActionEvent event) {
        StackPane content = (StackPane) parent.getScene().lookup("#content");
        Node page = ApplicationController.navBarController.getCurrentStack().firstElement();
        FlightCardController cardController = (FlightCardController) ApplicationController.navBarController.getCurrentStack().get(1).getUserData();

        ApplicationController.navBarController.getCurrentStack().clear();
        content.getChildren().clear();

        ApplicationController.navBarController.getCurrentStack().push(page);
        ApplicationController.navBarController.openPage();

        cardController.changeActionButtons();
    }

    public void setData(Reservation reservation) {
        this.reservation = reservation;
        Flight flight = reservation.getFlight();
        Seat selectedSeat = reservation.getSeat();
        Passenger passenger = reservation.getAccount().getPassenger();
        Airline airline = flight.getAirline();

        imgAirlineLogo.setImage(airline.getLogo());

        String depIATA = flight.getDepAirport().getIATA();
        String depICAO = flight.getDepAirport().getICAO();
        lblDepAirport.setText((depIATA != null) ? depIATA : depICAO);
        lblDepCity.setText(flight.getDepAirport().getCity() + " - " + flight.getDepAirport().getCountry());

        String arrIATA = flight.getArrAirport().getIATA();
        String arrICAO = flight.getArrAirport().getICAO();
        lblArrAirport.setText((arrIATA != null) ? arrIATA : arrICAO);
        lblArrCity.setText(flight.getArrAirport().getCity() + " - " + flight.getArrAirport().getCountry());

        lblPassengerName.setText(passenger.getFirstname() + " " + passenger.getLastname());
        Account.getCurrentUser().getPassenger().firstnameProperty().addListener((observable, oldValue, newValue) -> {
            lblPassengerName.setText(Account.getCurrentUser().getPassenger().getFirstname() + " " + Account.getCurrentUser().getPassenger().getLastname());
        });
        lblFlightID.setText(String.valueOf(flight.getId()));

        lblSelectedSeat.setText(selectedSeat.getColumn()+selectedSeat.getRow());
        lblClassType.setText(selectedSeat.getType() + " class");

        lblDepDateTime.setText(flight.getDepDatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm")));
        lblArrDateTime.setText(flight.getArrDatetime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd  HH:mm")));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgAppLogo.setImage(new Image(getClass().getResource("/images/SkynetLogo.png").toExternalForm()));
    }
}
