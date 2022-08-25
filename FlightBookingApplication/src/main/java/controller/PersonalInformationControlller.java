package controller;

import data.AccountDao;
import data.AirportDao;
import data.PassengerDao;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import models.Account;
import models.Passenger;
import org.controlsfx.control.SearchableComboBox;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;



public class PersonalInformationControlller implements Initializable {

    @FXML
    private SearchableComboBox<String> countryBox;

    @FXML
    private ChoiceBox<String> genderBox;

    @FXML
    private DatePicker txtbirthday;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtfirstname;

    @FXML
    private TextField txtlastname;

    @FXML
    private Circle profilePictureFrame;
    private Image profilePicture;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        genderBox.getItems().addAll("Male","Female");
        countryBox.setItems(AirportDao.getCountryList());
        this.setData();

        txtbirthday.showingProperty().addListener((observableValue, wasFocused, isNowFocus) -> {
            if (isNowFocus && txtbirthday.getValue() == null) {
                txtbirthday.setValue(LocalDate.now().minusYears(20));
                Platform.runLater(()->{
                    txtbirthday.getEditor().clear();
                });
            }
        });

    }
  
   @FXML
   void setData () {
        Account user = Account.getCurrentUser();
        profilePicture = user.getPassenger().getProfilePictue();
        profilePictureFrame.setFill(new ImagePattern(profilePicture));
        txtfirstname.setText(user.getPassenger().getFirstname());
        txtlastname.setText(user.getPassenger().getLastname());
        txtbirthday.setValue(user.getPassenger().getBirthDate());
        txtemail.setText(user.getEmailAddress());
        if (user.getPassenger().getGender() != null) {
            genderBox.setValue(user.getPassenger().getGender().equals("M") ? "Male" : "Female");
        }
        countryBox.setValue(user.getPassenger().getCountry());
   }
   @FXML 
   void updateData(ActionEvent  event ) {
        Passenger passenger = new Passenger();
        PassengerDao passengerDao = new PassengerDao();
        Account account = new Account();
        AccountDao accountDao = new AccountDao();

        passenger.setProfilePictue(profilePicture);
        passenger.setFirstname(txtfirstname.getText().trim());
        passenger.setLastname(txtlastname.getText().trim());
        passenger.setBirthDate(txtbirthday.getValue());
        passenger.setGender(genderBox.getValue().substring(0,1));
        passenger.setCountry(countryBox.getValue());
        account.setEmailAddress(txtemail.getText().trim());

        passengerDao.update(Account.getCurrentUser().getPassenger().getId(),passenger);
        accountDao.update(Account.getCurrentUser().getId(), account);
   }

    @FXML
    void changeImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("IMAGE","*.png","*.jpg","*.jpeg")
        );

        File picturesDir = new File(System.getProperty("user.home") + "\\pictures");
        if (picturesDir.exists()) {
            fileChooser.setInitialDirectory(picturesDir);
        }

        File selectedFile = fileChooser.showOpenDialog(profilePictureFrame.getScene().getWindow());

        if (selectedFile != null) {
            profilePicture = new Image(selectedFile.getAbsolutePath());
            profilePictureFrame.setFill(new ImagePattern(profilePicture));
        }
    }

}
