package controller;

import data.FlightDao;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Account;
import models.Flight;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private ToggleButton btnArchive;

    @FXML
    private ToggleButton btnFavorite;

    @FXML
    private ToggleButton btnUpcoming;

    @FXML
    private StackPane calendarContainer;

    @FXML
    private StackPane parent;

    @FXML
    private VBox resultsContainer;

    @FXML
    private ToggleGroup tabsGroup;

    private final FilteredList<Flight> results = new FilteredList<>(FlightDao.getInstance().getFlightsList());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parent.getStylesheets().add(getClass().getResource("/style/HomePage.css").toExternalForm());

        alwaysOneSelected();

        DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        Node popupContent = datePickerSkin.getPopupContent();
        calendarContainer.getChildren().add(popupContent);

        getUpcomigFlights();
        tabsGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(btnUpcoming)) {
                getUpcomigFlights();
            }

            if (newValue.equals(btnFavorite)) {
                getFavoriteFlights();
            }

            if (newValue.equals(btnArchive)) {
                getArchiveFlights();
            }
        });

    }

    private void alwaysOneSelected() {
        tabsGroup.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
            if (newVal == null)
                oldVal.setSelected(true);
        });
    }

    private void getUpcomigFlights() {
        results.setPredicate(flight -> {
            if (Account.getCurrentUser().hasReservation(flight) && flight.getDepDatetime().isAfter(LocalDateTime.now())) {
                return true;
            }
            return false;
        });
        getData();
    }

    private void getFavoriteFlights() {
        results.setPredicate(flight -> {
            if (flight.isFavorite()) {
                return true;
            }
            return false;
        });
        getData();
    }

    private void getArchiveFlights() {
        results.setPredicate(flight -> {
            if (flight.getDepDatetime().isBefore(LocalDateTime.now())) {
                return true;
            }
            return false;
        });
        getData();
    }

    private void getData() {
        resultsContainer.getChildren().clear();
        for (Flight flight : results) {
            try {
                FXMLLoader cardLoader = new FXMLLoader(getClass().getResource("/view/FlightCard_Small.fxml"));
                HBox card = cardLoader.load();
                FlightCardController controller = cardLoader.getController();
                controller.setData(flight);
                resultsContainer.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
