package controller;

import data.AccountDao;
import data.PassengerDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Account;
import models.Passenger;
import view.Palette;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignupController implements Initializable {

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private Label labelMessage;

    @FXML
    private AnchorPane parent;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstname;

    @FXML
    private TextField txtLastname;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parent.getStylesheets().add(getClass().getResource("/style/Sign-up.css").toExternalForm());
    }

    public void SwitchtoSignin(ActionEvent e) throws IOException {
        Parent root =FXMLLoader.load(getClass().getResource("/view/Signin.fxml"));
        Stage stage = (Stage) parent.getScene().getWindow();
        Scene scene = new Scene(root);
        Palette.getDefaultPalette().usePalette(scene);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void signUp(ActionEvent event) throws IOException {
        if (txtFirstname.getText().isBlank() || txtLastname.getText().isBlank() || txtEmail.getText().isBlank() ||
            txtUsername.getText().isBlank() || txtPassword.getText().isBlank() || birthDatePicker.getValue() == null
        ) {
            labelMessage.setText("All fields are required");
            return;
        }
        else {
            labelMessage.setText("");
        }

        AccountDao accountDao = new AccountDao();
        Account account = new Account();

        PassengerDao passengerDao = new PassengerDao();
        Passenger passenger = new Passenger();

        passenger.setFirstname(txtFirstname.getText().trim());
        passenger.setLastname(txtLastname.getText().trim());
        passenger.setBirthDate(birthDatePicker.getValue());

        passengerDao.create(passenger);
        account.setPassenger(passenger.getId());

        if (accountDao.read(txtUsername.getText()) != null) {
            labelMessage.setText("This username is already taken");
        }
        else {
            account.setUsername(txtUsername.getText().trim());
            account.setPassword(txtPassword.getText().trim());
            account.setEmailAddress(txtEmail.getText().trim());
            accountDao.create(account);
            this.SwitchtoSignin(new ActionEvent());
        }
    }
}
