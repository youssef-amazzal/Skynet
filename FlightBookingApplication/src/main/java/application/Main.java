package application;

import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import view.Palette;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {

          	HBox root = FXMLLoader.load(getClass().getResource("/view/Application.fxml"));
			root.getStylesheets().add(getClass().getResource("/style/Application.css").toExternalForm());

			Scene scene = new Scene(root, 1366, 768);

			Palette DarkPalette = new Palette(
							"DarkMode",
							"#1e1f24",
							"#23232f",
							"#3f98fc"
					);

			Palette LightPalette = new Palette(
							"LightMode",
							"#eef1fa",
							"white",
							"#3f98fc"
					);


			LightPalette.usePalette(scene);
			DarkPalette.usePalette(scene);

			CSSFX.start();
			primaryStage.setScene(scene);
			primaryStage.setTitle("Flight Booking Application");
			primaryStage.show();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
