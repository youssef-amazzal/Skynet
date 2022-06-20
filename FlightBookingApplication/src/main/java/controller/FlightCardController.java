package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class FlightCardController implements Initializable {

    @FXML
    private HBox parent;

    @FXML
    private Button btnBook;

    @FXML
    private ImageView AgencyLogo;

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
            Parent page = FXMLLoader.load(getClass().getResource("/view/SearchPage/SeatMap.fxml"));
            VBox.setVgrow(page, Priority.ALWAYS);

            VBox content = (VBox) parent.getScene().lookup("#content");
            content.getChildren().clear();
            content.getChildren().add(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
