package controller;

import data.AirportDao;
import data.FlightDao;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Flight;
import org.controlsfx.control.SearchableComboBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.ResourceBundle;

public class SearchPageController implements Initializable {

    @FXML
    private SearchableComboBox<String> arrCity;

    @FXML
    private SearchableComboBox<String> arrCountry;

    @FXML
    private SearchableComboBox<String> depCity;

    @FXML
    private SearchableComboBox<String> depCountry;

    @FXML
    private DatePicker depDateAfter;

    @FXML
    private DatePicker depDateBefore;

    @FXML
    private ChoiceBox<String> inputSortBox;

    @FXML
    private Label lblResultsCounter;

    @FXML
    private Pagination pagination;

    @FXML
    private StackPane parent;

    @FXML
    private ScrollPane scrollPane;

    private final FilteredList<Flight> results = new FilteredList<>(FlightDao.getInstance().getFlightsList());
    private final SortedList<Flight> sortedResults = new SortedList<>(results);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parent.getStylesheets().add(getClass().getResource("/style/SearchPage.css").toExternalForm());
        lblResultsCounter.setText("Results(" + results.size() + ")");

        depCountry.setItems(AirportDao.getCountryList());
        arrCountry.setItems(AirportDao.getCountryList());

        depCountry.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> depCity.setItems(AirportDao.getCityList(newValue)));
        arrCountry.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> arrCity.setItems(AirportDao.getCityList(newValue)));

        // Set up ComboBox
        inputSortBox.getItems().addAll("","Closest Date", "Furthest Date");
        inputSortBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Closest Date")) {
                sortByClosestDate();
            }
            else if (newValue.equals("Furthest Date")) {
                sortByFurthestDate();
            }
            else {
                stopSorting();
            }
        });


        // set up the pagination
        pagination.setMaxPageIndicatorCount(10);
        int itemsPerPage = 5;
        int nbrPages = (int) Math.ceil((double) sortedResults.size() / itemsPerPage);
        pagination.setPageCount(nbrPages == 0 ? 1 : nbrPages);

        pagination.setPageFactory((pageIndex) -> {
            VBox page = new VBox();

            int firstItemIndex = pageIndex * itemsPerPage;
            int lastItemIndex = (pageIndex + 1) * itemsPerPage;


            for (int i = firstItemIndex; i < Math.min(lastItemIndex, sortedResults.size()) ; i++) {
                Flight flight = sortedResults.get(i);
                try {
                    FXMLLoader cardLoader = new FXMLLoader(getClass().getResource("/view/SearchPage/FlightCard.fxml"));
                    HBox card = cardLoader.load();
                    FlightCardController controller = cardLoader.getController();
                    controller.setData(flight);
                    page.getChildren().add(card);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return page;
        });

        search(new ActionEvent());
    }

    @FXML
    void goToTop(ActionEvent event) {
        scrollPane.setVvalue(0);
    }

    @FXML
    void swapData(MouseEvent event) {
        int tempDepCountry = depCountry.getSelectionModel().getSelectedIndex();
        int tempDepCity = depCity.getSelectionModel().getSelectedIndex();

        int tempArrCountry = arrCountry.getSelectionModel().getSelectedIndex();
        int tempArrCity = arrCity.getSelectionModel().getSelectedIndex();

        depCountry.getSelectionModel().select(tempArrCountry);
        depCity.getSelectionModel().select(tempArrCity);

        arrCountry.getSelectionModel().select(tempDepCountry);
        arrCity.getSelectionModel().select(tempDepCity);
    }

    @FXML
    void search(ActionEvent event) {

        results.setPredicate(flight -> {
            if (flight.getDepDatetime().isBefore(LocalDateTime.now())) {
                return false;
            }

            if (depCountry.getSelectionModel().getSelectedItem() != null && !depCountry.getSelectionModel().getSelectedItem().isBlank()) {
                if (!flight.getDepAirport().getCountry().equals(depCountry.getSelectionModel().getSelectedItem())) {
                    return false;
                }
            }

            if (arrCountry.getSelectionModel().getSelectedItem() != null && !arrCountry.getSelectionModel().getSelectedItem().isBlank()) {
                if (!flight.getArrAirport().getCountry().equals(arrCountry.getSelectionModel().getSelectedItem())) {
                    return false;
                }
            }

            if (depCity.getSelectionModel().getSelectedItem() != null && !depCity.getSelectionModel().getSelectedItem().isBlank()) {
                if (!flight.getDepAirport().getCity().equals(depCity.getSelectionModel().getSelectedItem())) {
                    return false;
                }
            }

            if (arrCity.getSelectionModel().getSelectedItem() != null && !arrCity.getSelectionModel().getSelectedItem().isBlank()) {
                if (!flight.getArrAirport().getCity().equals(arrCity.getSelectionModel().getSelectedItem())) {
                    return false;
                }
            }

            if (depDateAfter.getValue() != null) {
                if (flight.getDepDatetime().toLocalDate().isBefore(depDateAfter.getValue())) {
                    return false;
                }
            }

            if (depDateBefore.getValue() != null) {
                if (flight.getDepDatetime().toLocalDate().isAfter(depDateBefore.getValue())) {
                    return false;
                }
            }

            return true;

        });

        lblResultsCounter.setText("Results(" + results.size() + ")");

        Platform.runLater(this::refreshPage);
    }

    private void sortByClosestDate() {
        sortedResults.setComparator((flight1, flight2) -> {
            if (flight1.getDepDatetime().isAfter(flight2.getDepDatetime())) {
                return 1;
            }

            if (flight1.getDepDatetime().isBefore(flight2.getDepDatetime())) {
                return -1;
            }

            return 0;
        });
        Platform.runLater(this::refreshPage);
    }

    private void sortByFurthestDate() {
        sortedResults.setComparator((flight1, flight2) -> {
            if (flight1.getDepDatetime().isAfter(flight2.getDepDatetime())) {
                return -1;
            }

            if (flight1.getDepDatetime().isBefore(flight2.getDepDatetime())) {
                return 1;
            }

            return 0;
        });
        Platform.runLater(this::refreshPage);
    }

    private void refreshPage() {
        int itemsPerPage = 5;
        int nbrPages = (int) Math.ceil((double) sortedResults.size() / itemsPerPage);
        pagination.setPageCount(Integer.MAX_VALUE);
        pagination.setPageCount(nbrPages == 0 ? 1 : nbrPages);
    }
    private void stopSorting() {
        sortedResults.setComparator(Comparator.comparingInt(Flight::getId).reversed());
        Platform.runLater(this::refreshPage);
    }

    @FXML
    private void openSortBox() {
        inputSortBox.show();
    }
}
