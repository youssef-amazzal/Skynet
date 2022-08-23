package controller;

import data.FlightDao;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.control.skin.PaginationSkin;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
import java.util.Objects;
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

    private final FilteredList<Flight> results = new FilteredList<>(FlightDao.getInstance().getFlightsList());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parent.getStylesheets().add(getClass().getResource("/style/HomePage.css").toExternalForm());
        alwaysOneSelected();

        {
            lblFirstName.setText(Account.getCurrentUser().getPassenger().getFirstname());
            Account.getCurrentUser().getPassenger().firstnameProperty().addListener((observable, oldValue, newValue) -> {
                lblFirstName.setText(newValue);
            });
            Image picture = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/ProfilePicture.png")));
            profilePicture.setFill(new ImagePattern(picture));
        }


        {
            DatePickerSkin datePickerSkin = new DatePickerSkin(new DatePicker(LocalDate.now()));
            Node popupContent = datePickerSkin.getPopupContent();
            calendarContainer.getChildren().add(popupContent);
        }

        {
            // set up the pagination
            pagination.setMaxPageIndicatorCount(5);

            int itemsPerPage = 4;
            int nbrPages = (int) Math.ceil((double) results.size() / itemsPerPage);
            pagination.setPageCount(nbrPages == 0 ? 1 : nbrPages);

            pagination.setPageFactory((pageIndex) -> {
                VBox page = new VBox();

                //System.out.println(results.size());

                int firstItemIndex = pageIndex * itemsPerPage;
                int lastItemIndex = (pageIndex + 1) * itemsPerPage;


                for (int i = firstItemIndex; i < Math.min(lastItemIndex, results.size()); i++) {
                    Flight flight = results.get(i);
                    try {
                        FXMLLoader cardLoader = new FXMLLoader(getClass().getResource("/view/FlightCard_Small.fxml"));
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

        {
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

                refreshPagination();
            });

            btnUpcoming.setSelected(true);
        }
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
    }

    private void getFavoriteFlights() {
        results.setPredicate(flight -> {
            if (flight.isFavorite()) {
                return true;
            }
            return false;
        });
    }

    private void getArchiveFlights() {
        results.setPredicate(flight -> {
            if (Account.getCurrentUser().hasReservation(flight) && flight.getDepDatetime().isBefore(LocalDateTime.now())) {
                return true;
            }
            return false;
        });
    }

    private void refreshPagination() {
        int itemsPerPage = 4;
        int nbrPages = (int) Math.ceil((double) results.size() / itemsPerPage);
        pagination.setPageCount(Integer.MAX_VALUE);
        pagination.setPageCount(nbrPages == 0 ? 1 : nbrPages);
    }
}
