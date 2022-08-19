package controller;

import data.AccountDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Account;
import view.Palette;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SigninController implements Initializable {

	@FXML
	private Label labelMessage;

	@FXML
	private AnchorPane parent;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private TextField txtUsername;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		parent.getStylesheets().add(getClass().getResource("/style/Sign-in.css").toExternalForm());
	}

	@FXML
	void login(ActionEvent event) {

		if (txtUsername.getText().isBlank() || txtPassword.getText().isBlank()) {
			labelMessage.setText("All fields are required");
			return;
		}

		AccountDao accountDao = new AccountDao();
		Account user = accountDao.read(txtUsername.getText().trim());

		if (user == null) {
			labelMessage.setText("This User doesn't exist.");
		}
		else {
			if (user.getPassword().equals(txtPassword.getText().trim())) {

				Account.setCurrentUser(user);

				Stage currentStage = (Stage) parent.getScene().getWindow();
				currentStage.close();

				ApplicationController.appLoad(new Stage());

			}
			else {
				labelMessage.setText("Wrong Password");
			}
		}
	}

	public void SwitchtoSignup(ActionEvent e) throws IOException {
		Parent root =FXMLLoader.load(getClass().getResource("/view/Signup.fxml"));
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		Palette.getDefaultPalette().usePalette(scene);
		stage.setScene(scene);
		stage.show();
	}
}
