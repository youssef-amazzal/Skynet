package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Flight;

import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;
import java.util.ResourceBundle;

public class FlightCardController implements Initializable {

    @FXML
    private ImageView AgencyLogo;

    @FXML
    private Label lblAirLine;

    @FXML
    private Label lblArrAirport;

    @FXML
    private Label lblArrCity;

    @FXML
    private Label lblArrDateTime;

    @FXML
    private Label lblDepAirport;

    @FXML
    private Label lblDepCity;

    @FXML
    private Label lblDepDateTime;

    @FXML
    private Label lblDuration;

    @FXML
    private HBox parent;

    private Flight flight;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parent.getStylesheets().add(getClass().getResource("/style/FlightCard.css").toExternalForm());

        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/RoyalAirMaroc.png")));
        AgencyLogo.setImage(logo);
        HBox viewPort = (HBox) AgencyLogo.getParent();
        AgencyLogo.setFitHeight(viewPort.getHeight());
    }

    @FXML
    void bookNow(ActionEvent event) {
        try {
            FXMLLoader flightLoader = new FXMLLoader(getClass().getResource("/view/SearchPage/SeatMap.fxml"));

            Parent page = flightLoader.load();
            VBox.setVgrow(page, Priority.ALWAYS);

            SeatMapController flightController = flightLoader.getController();
            flightController.setData(flight);

            StackPane content = (StackPane) parent.getScene().lookup("#content");
            content.getChildren().add(page);

            ApplicationController.searchPageStack.push(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setData(Flight flight) {
        this.flight = flight;
        lblDepAirport.setText(flight.getDepAirport().getIATA());
        lblDepCity.setText(flight.getDepAirport().getCity());
        lblDepDateTime.setText(flight.getDepDatetime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));

        lblAirLine.setText(flight.getAirline().getName());
        long durationInSeconds = Duration.between(flight.getDepDatetime(), flight.getArrDatetime()).getSeconds();
        lblDuration.setText(String.format("%dH%dM", durationInSeconds / 3600, (durationInSeconds % 3600) / 60));

        lblArrAirport.setText(flight.getArrAirport().getIATA());
        lblArrCity.setText(flight.getArrAirport().getCity());
        lblArrDateTime.setText(flight.getArrDatetime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
    }
}
