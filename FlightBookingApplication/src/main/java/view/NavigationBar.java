package view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class NavigationBar {
	private static VBox navigationBar;
	private static FXMLLoader navigationBarLoader;
	
	private NavigationBar() {
		try {
			navigationBarLoader = new FXMLLoader(getClass().getResource("/view/NavigationBar.fxml"));
			
			navigationBar = navigationBarLoader.load();
			navigationBar.getStylesheets().add(getClass().getResource("/style/navigationBar.css").toExternalForm());
			
			this.alwaysOneSelected();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static VBox getNavigationBar() {
		
		if (navigationBar == null) {
			new NavigationBar();
		}
		return navigationBar;
	}
	
	
	private void alwaysOneSelected() {
		ToggleGroup toggleGroup = (ToggleGroup) navigationBarLoader.getNamespace().get("navigationbarGroup");
		toggleGroup.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
		    if (newVal == null)
		        oldVal.setSelected(true);
		});
	}
}
