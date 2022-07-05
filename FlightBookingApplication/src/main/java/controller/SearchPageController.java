package controller;

import data.AirportDao;
import data.FlightDao;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import models.Flight;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchPageController implements Initializable {

    @FXML
    private StackPane parent;

    @FXML
    private Button SearchButton;

    @FXML
    private Button topButton;

    @FXML
    private ComboBox<String> inputArrivalCity;

    @FXML
    private ComboBox<String> inputDepartureCity;

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
    private Label swapButton;

    private final FlightDao flightDao = new FlightDao();
    private FilteredList<String> filteredDepCityList;
    private FilteredList<String> filteredArrCityList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parent.getStylesheets().add(getClass().getResource("/style/SearchPage.css").toExternalForm());

        //add search results
        getData();

        filteredDepCityList = new FilteredList<String>(AirportDao.getCityList());
        filteredArrCityList = new FilteredList<String>(AirportDao.getCityList());

        inputDepartureCity.setItems(filteredDepCityList);
        inputArrivalCity.setItems(filteredArrCityList);

    }

    @FXML
    void goToTop(ActionEvent event) {
        scrollPane.setVvalue(0);
    }

    private void getData() {
        List<Flight> results = new ArrayList<>(flightDao.readAll());

        for (Flight flight : results) {
            try {
                FXMLLoader cardLoader = new FXMLLoader(getClass().getResource("/view/SearchPage/FlightCard.fxml"));
                HBox card = cardLoader.load();
                FlightCardController controller = cardLoader.getController();
                controller.setData(flight);
                searchPage.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    }
}
