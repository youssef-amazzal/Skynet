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
			//primaryStage.show();
			/*
			Connection conn = DataSource.getConnection();
			PreparedStatement query = conn.prepareStatement("SELECT date('now');");
			ResultSet res = query.executeQuery();
			if (res.next()) {
				System.out.println(res.getString(1));
			}
			*/

			PassengerDao pDao = new PassengerDao();
			Passenger passenger = pDao.read(1);

			System.out.println("Firstname: " + passenger.getFirstname() + "\tLastname: " + passenger.getLastname());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
