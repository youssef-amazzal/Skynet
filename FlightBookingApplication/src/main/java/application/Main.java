package application;

import fr.brouillard.oss.cssfx.CSSFX;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
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
			root.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/Application.css")).toExternalForm());
			Scene scene = new Scene(root, 1366, 768);

			StackPane page = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/SearchPage.fxml")));
			page.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/style/SearchPage.css")).toExternalForm());

			root.getChildren().addAll(NavigationBar.getNavigationBar(), page);
			HBox.setHgrow(page, Priority.ALWAYS);


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
			//DarkPalette.usePalette(scene);

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
