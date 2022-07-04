package application;

import data.PassengerDao;
import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Passenger;
import view.Palette;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {

          	Parent root = FXMLLoader.load(getClass().getResource("/view/Signin.fxml"));

			Scene scene = new Scene(root);

			Palette.setDefaultPalette(Palette.LightPalette);
			Palette.getDefaultPalette().usePalette(scene);

			CSSFX.start();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Flight Booking Application");
			primaryStage.show();

			/*PassengerDao pDao = new PassengerDao();
			Passenger passenger = pDao.read(1);

			System.out.println(""
					+"Firstname: " + passenger.getFirstname()
					+"\tLastname: " + passenger.getLastname()
					+"\tBirthDate: " + passenger.getBirthDate().toString());*/
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
