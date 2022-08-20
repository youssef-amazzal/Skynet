package controller;

import data.AirportDao;
import data.FlightDao;
import javafx.collections.transformation.FilteredList;
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
import java.util.ResourceBundle;

public class SearchPageController implements Initializable {

    @FXML
    private StackPane parent;
    @FXML
    private Button topButton;

    @FXML
    private SearchableComboBox<String> inputArrivalCity;

    @FXML
    private SearchableComboBox<String> inputDepartureCity;

    @FXML
    private DatePicker inputDepartureDate;

    @FXML
    private TextField inputPrice;

    @FXML
    private TextField inputSearch;

    @FXML
    private ChoiceBox<?> inputSortBox;

    @FXML
    private Label lblResultsCounter;

    @FXML
    private VBox searchPage;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Pagination pagination;

    private final FilteredList<Flight> results = new FilteredList<>(FlightDao.getInstance().getFlightsList());


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parent.getStylesheets().add(getClass().getResource("/style/SearchPage.css").toExternalForm());

        inputDepartureCity.setItems(AirportDao.getCityList());
        inputArrivalCity.setItems(AirportDao.getCityList());

        int itemsPerPage = 10;
        int nbrPages = (int) Math.ceil((double) results.size() / itemsPerPage);
        pagination.setPageCount(nbrPages == 0 ? 1 : nbrPages);
        pagination.setMaxPageIndicatorCount(5);

        pagination.setPageFactory((pageIndex) -> {
            VBox page = new VBox();

            int firstItemIndex = pageIndex * itemsPerPage;
            int lastItemIndex = (pageIndex + 1) * itemsPerPage;

            for (int i = firstItemIndex; i < Math.min(lastItemIndex, results.size()) ; i++) {
                Flight flight = results.get(i);
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

    }

    @FXML
    void goToTop(ActionEvent event) {
        scrollPane.setVvalue(0);
    }

    @FXML
    void swapCities(MouseEvent event) {
        int tempCity = inputDepartureCity.getSelectionModel().getSelectedIndex();
        inputDepartureCity.getSelectionModel().select(inputArrivalCity.getSelectionModel().getSelectedIndex());
        inputArrivalCity.getSelectionModel().select(tempCity);
    }

    @FXML
    void search(ActionEvent event) {

        results.setPredicate(flight -> {
            if (inputDepartureCity.getSelectionModel().getSelectedItem() != null && !inputDepartureCity.getSelectionModel().getSelectedItem().isBlank()) {
                if (!flight.getDepAirport().getCity().equals(inputDepartureCity.getSelectionModel().getSelectedItem())) {
                    return false;
                }
            }

            if (inputArrivalCity.getSelectionModel().getSelectedItem() != null && !inputArrivalCity.getSelectionModel().getSelectedItem().isBlank()) {
                if (!flight.getArrAirport().getCity().equals(inputArrivalCity.getSelectionModel().getSelectedItem())) {
                    return false;
                }
            }

            if (inputDepartureDate.getValue() != null) {
                if (flight.getDepDatetime().toLocalDate().isBefore(inputDepartureDate.getValue())) {
                    return false;
                }
            }

            return true;

        });

        pagination.setCurrentPageIndex(0);
    }
}
