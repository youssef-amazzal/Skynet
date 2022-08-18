package controller;

import data.AccountDao;
import data.PassengerDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import models.Account;
import models.Passenger;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;



public class PersonalInformationControlller implements Initializable {

    @FXML
    private Circle profilePicture;
    
    @FXML
    private DatePicker txtbirthday;

    @FXML
    private TextField txtcountry;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtfirstname;

    @FXML
    private TextField txtgender;

    @FXML
    private TextField txtlastname;

    @FXML
    private TextField txtnationality;

    @FXML
    private TextField txtphone;
    
    @FXML
    private Label labelMessage;





    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image picture = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/ProfilePicture.png")));
        profilePicture.setFill(new ImagePattern(picture));
        
        this.setData();
    }
  
   @FXML
   void setData () {
       
    	txtfirstname.setText(Account.getCurrentUser().getPassenger().getFirstname());
    	txtlastname.setText(Account.getCurrentUser().getPassenger().getLastname());
    	txtbirthday.setValue(Account.getCurrentUser().getPassenger().getBirthDate());
    	txtemail.setText(Account.getCurrentUser().getEmailAddress());
    	
   }
   @FXML 
   void updateData(ActionEvent  event ) {
	   
	   Passenger passenger = new Passenger();
       PassengerDao passengerDao = new PassengerDao();
       Account account = new Account();
       AccountDao accountDao = new AccountDao();
       
       
       passenger.setFirstname(txtfirstname.getText().trim());
       passenger.setLastname(txtlastname.getText().trim());
       passenger.setBirthDate(txtbirthday.getValue());
       account.setEmailAddress(txtemail.getText().trim());
       
       passengerDao.update(Account.getCurrentUser().getPassenger().getId(),passenger);
       accountDao.update(Account.getCurrentUser().getId(), account);
   }

   }
