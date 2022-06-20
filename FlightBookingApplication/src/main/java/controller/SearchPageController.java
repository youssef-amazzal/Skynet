package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SearchPageController implements Initializable {

    @FXML
    private StackPane parent;

    @FXML
    private Button SearchButton;

    @FXML
    private Button topButton;

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
    private ScrollPane scrollPane;

    @FXML
    private Label swapButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parent.getStylesheets().add(getClass().getResource("/style/SearchPage.css").toExternalForm());

        //add search results
        for (int i = 0; i < 10; i++) {
            try {
                FXMLLoader cardLoader = new FXMLLoader(getClass().getResource("/view/SearchPage/FlightCard.fxml"));
                HBox card = cardLoader.load();
                searchPage.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void goToTop(ActionEvent event) {
        scrollPane.setVvalue(0);
    }
}
