package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SearchPageController implements Initializable {

    @FXML
    private Button SearchButton;

    @FXML
    private ComboBox<?> inputArrivalCity;

    @FXML
    private ComboBox<?> inputDepartureCity;

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
    private Label swapButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < 10; i++) {
            try {
                FXMLLoader cardLoader = new FXMLLoader(getClass().getResource("/view/FlightCard.fxml"));
                HBox card = cardLoader.load();
                card.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/FlightCard.css")).toExternalForm());
                searchPage.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
