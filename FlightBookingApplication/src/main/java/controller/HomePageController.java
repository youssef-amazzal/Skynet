package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.StackPane;
import models.Flight;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private TableView<Flight> flightsTable;

    @FXML
    private StackPane parent;

    @FXML
    private StackPane calendarContainer;

    @FXML
    private ToggleGroup tabsGroup;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parent.getStylesheets().add(getClass().getResource("/style/HomePage.css").toExternalForm());

        DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
        Node popupContent = datePickerSkin.getPopupContent();
        calendarContainer.getChildren().add(popupContent);
    }
}
