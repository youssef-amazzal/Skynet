package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PersonalInformationControlller implements Initializable {

    @FXML
    private Circle profilePicture;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image picture = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/ProfilePicture.jpg")));
        profilePicture.setFill(new ImagePattern(picture));
    }
}
