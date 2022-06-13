package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import view.NavigationBar;

public class Main extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			HBox root = new HBox();
			root.getStylesheets().add(getClass().getResource("/style/Application.css").toExternalForm());
			Scene scene = new Scene(root, 1366, 768);
			
			root.getChildren().add(NavigationBar.getNavigationBar());
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Flight Booking Application");
			primaryStage.show();			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
