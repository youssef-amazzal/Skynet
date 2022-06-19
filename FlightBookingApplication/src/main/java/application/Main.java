package application;

import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.NavigationBar;
import view.Palette;

import java.util.Objects;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {

          	HBox root = new HBox();
			root.getStylesheets().add(getClass().getResource("/style/application.css").toExternalForm());

			HBox page =  FXMLLoader.load(getClass().getResource("/view/accountPage/AccountPage.fxml"));
			page.getStylesheets().add(getClass().getResource("/style/AccountPage.css").toExternalForm());
			//page.getChildren().add();
			HBox.setHgrow(page, Priority.ALWAYS);
			root.getChildren().addAll(NavigationBar.getNavigationBar(), page);

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
