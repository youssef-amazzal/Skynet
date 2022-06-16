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
  private Scene scene;
  private Stage  stage;
  private Parent root;
  
  public void SwitchtoSignin(ActionEvent e) throws IOException {
	  Parent root =FXMLLoader.load(getClass().getResource("/view/Signin.fxml"));
	  stage=(Stage)((Node)e.getSource()).getScene().getWindow();
	  scene=new Scene(root);
	  stage.setScene(scene);
	  stage.show();
  }
}
