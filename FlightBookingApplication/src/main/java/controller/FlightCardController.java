package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class FlightCardController implements Initializable {

    @FXML
    ImageView AgencyLogo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image logo = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/RoyalAirMaroc.png")));
        AgencyLogo.setImage(logo);
        HBox viewPort = (HBox) AgencyLogo.getParent();
        AgencyLogo.setFitHeight(viewPort.getHeight());
    }
}
