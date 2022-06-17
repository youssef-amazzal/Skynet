package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SwitchSigninController {

  public void SwitchtoSignin(ActionEvent e) throws IOException {
	  Parent root =FXMLLoader.load(getClass().getResource("/view/Signin.fxml"));
      root.getStylesheets().add(getClass().getResource("/style/Sign-in.css").toExternalForm());
      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
      Scene scene = new Scene(root);
	  stage.setScene(scene);
	  stage.show();
  }
}
