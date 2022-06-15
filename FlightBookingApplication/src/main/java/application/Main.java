package application;

import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import view.NavigationBar;
import java.util.Objects;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			HBox root = new HBox();
			root.getStylesheets().add(getClass().getResource("/style/Application.css").toExternalForm());
			root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/Application.css")).toExternalForm());
			Scene scene = new Scene(root, 1366, 768);
			
			root.getChildren().add(NavigationBar.getNavigationBar());
			ScrollPane page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SearchPage.fxml")));
			page.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/SearchPage.css")).toExternalForm());

			root.getChildren().addAll(NavigationBar.getNavigationBar(), page);
			HBox.setHgrow(page, Priority.ALWAYS);


			Palette DarkPalette = new Palette(
					"DarkMode",
					"#23232f",
					"#1e1f24",
					"3f98fc"
			);

			Palette LightPalette = new Palette(
					"LightMode",
					"#eef1fa",
					"white",
					"3f98fc"
			);

			LightPalette.usePalette(scene);


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
