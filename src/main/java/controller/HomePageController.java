package controller;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import models.Account;
import models.Flight;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
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
    private Circle profilePicture;

    @FXML
    private Label lblFirstName;

    @FXML
    private StackPane parent;

    @FXML
    private Pagination pagination;

    @FXML
    private ToggleGroup tabsGroup;

    @FXML
    private ChoiceBox<String> inputSortBox;

    @FXML
    private Label lblResultsCounter;

    private final FilteredList<Flight> results = new FilteredList<>(Account.getCurrentUser().getReservedFlights());
    private final SortedList<Flight> sortedResults = new SortedList<>(results);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parent.getStylesheets().add(getClass().getResource("/style/HomePage.css").toExternalForm());
        alwaysOneSelected();
        btnUpcoming.setDisable(true);
        btnFavorite.setDisable(true);
        btnArchive.setDisable(true);

        {
            lblFirstName.textProperty().bind(Account.getCurrentUser().getPassenger().firstnameProperty());
            profilePicture.setFill(new ImagePattern(Account.getCurrentUser().getPassenger().getProfilePictue()));
            Account.getCurrentUser().getPassenger().profilePictueProperty().addListener((observable, oldValue, newValue) -> {
                profilePicture.setFill(new ImagePattern(newValue));
            });
        }


        {
            DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
            Node popupContent = datePickerSkin.getPopupContent();
            calendarContainer.getChildren().add(popupContent);
        }

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
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageCount(1);
        int itemsPerPage = 4;

        {
            pagination.setPageFactory((pageIndex) -> {
                VBox page = new VBox();

                int firstItemIndex = pageIndex * itemsPerPage;
                int lastItemIndex = (pageIndex + 1) * itemsPerPage;

                new Thread(() -> {
                    for (int i = firstItemIndex; i < Math.min(lastItemIndex, sortedResults.size()); i++) {
                        Flight flight = sortedResults.get(i);
                        try {
                            final FXMLLoader cardLoader = new FXMLLoader(getClass().getResource("/view/FlightCard_Small.fxml"));
                            HBox card = cardLoader.load();
                            final FlightCardController controller = cardLoader.getController();
                            controller.setData(flight);
                            Platform.runLater(() -> {
                                page.getChildren().add(card);
                            });
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
                return page;
            });

        }

        btnUpcoming.setDisable(false);
        btnFavorite.setDisable(false);
        btnArchive.setDisable(false);
        btnUpcoming.setSelected(true);
        btnUpcoming.setOnAction(e -> getUpcomingFlights());
        btnFavorite.setOnAction(e -> getFavoriteFlights());
        btnArchive.setOnAction(e -> getArchiveFlights());
        Platform.runLater(this::getUpcomingFlights);
    }

    private void alwaysOneSelected() {
        tabsGroup.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
            if (newVal == null)
                oldVal.setSelected(true);
        });
    }

    private void getUpcomingFlights() {
        new Thread(() -> {
            ObservableList<Flight> source = (ObservableList<Flight> ) results.getSource();
            source.clear();
            source.addAll(Account.getCurrentUser().getReservedFlights());
            results.setPredicate(null);
            results.setPredicate(flight -> flight.getDepDatetime().isAfter(LocalDateTime.now()));

            Platform.runLater(() -> {
                lblResultsCounter.setText("Results(" + results.size() + ")");
                refreshPagination();
            });
        }).start();
    }
    private void getFavoriteFlights() {
        new Thread(() -> {
            ObservableList<Flight> source = (ObservableList<Flight>) results.getSource();
            source.clear();
            source.addAll(Account.getCurrentUser().getFavoriteFlights());
            results.setPredicate(null);
            results.setPredicate(flight -> true);

            Platform.runLater(() -> {
                lblResultsCounter.setText("Results(" + results.size() + ")");
                refreshPagination();
            });
        }).start();
    }
    private void getArchiveFlights() {
        new Thread(() -> {
            ObservableList<Flight> source = (ObservableList<Flight>) results.getSource();
            source.clear();
            source.addAll(Account.getCurrentUser().getReservedFlights());
            results.setPredicate(null);
            results.setPredicate(flight -> flight.getDepDatetime().isBefore(LocalDateTime.now()));

            Platform.runLater(() -> {
                lblResultsCounter.setText("Results(" + results.size() + ")");
                refreshPagination();
            });
        }).start();
    }
    private void refreshPagination() {
        int itemsPerPage = 4;
        int nbrPages = (int) Math.ceil((double) sortedResults.size() / itemsPerPage);
        pagination.setPageCount(Integer.MAX_VALUE);
        pagination.setPageCount(nbrPages == 0 ? 1 : nbrPages);
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
        Platform.runLater(this::refreshPagination);
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
        Platform.runLater(this::refreshPagination);
    }

    private void stopSorting() {
        sortedResults.setComparator(Comparator.comparingInt(Flight::getId).reversed());
        Platform.runLater(this::refreshPagination);
    }

}
