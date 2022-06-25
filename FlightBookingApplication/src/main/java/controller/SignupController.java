package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import view.Palette;

public class SignupController implements Initializable {

    @FXML
    private AnchorPane parent;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parent.getStylesheets().add(getClass().getResource("/style/sign-up.css").toExternalForm());
    }

  public void SwitchtoSignin(ActionEvent e) throws IOException {
	  Parent root =FXMLLoader.load(getClass().getResource("/view/Signin.fxml"));
      root.getStylesheets().add(getClass().getResource("/style/Sign-in.css").toExternalForm());
      Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
      Scene scene = new Scene(root);
      Palette.getDefaultPalette().usePalette(scene);
	  stage.setScene(scene);
	  stage.show();
  }
}
