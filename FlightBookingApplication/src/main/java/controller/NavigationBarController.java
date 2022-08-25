package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import view.Palette;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;

public class NavigationBarController implements Initializable {

    @FXML
    private ToggleButton accounBtn;

    @FXML
    private ImageView appLogo;

    @FXML
    private ToggleButton homeBtn;

    @FXML
    private VBox navigationBar;

    @FXML
    private ToggleGroup navigationBarGroup;

    @FXML
    private ToggleButton searchBtn;

    @FXML
    private ToggleButton themeButton;

    private final ObservableList<Node> searchPages = FXCollections.observableList(ApplicationController.searchPageStack);

    private final ObservableList<Node> homePages = FXCollections.observableList(ApplicationController.homePageStack);
    private Node accountPages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navigationBar.getStylesheets().add(getClass().getResource("/style/NavigationBar.css").toExternalForm());
        VBox.setVgrow(navigationBar, Priority.ALWAYS);

        appLogo.setImage(new Image(getClass().getResource("/images/SkynetLogo.png").toExternalForm()));
        alwaysOneSelected();

        if (Palette.getDefaultPalette().equals(Palette.DarkPalette)) {
            themeButton.setSelected(true);
        }
    }

    private void alwaysOneSelected() {
        navigationBarGroup.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
            if (newVal == null)
                oldVal.setSelected(true);
        });
    }

    private Node loadPage (String path) {
        Parent page = null;
        try {
            FXMLLoader pageLoader = new FXMLLoader(getClass().getResource(path));
            page = pageLoader.load();
            VBox.setVgrow(page, Priority.ALWAYS);
            page.setUserData(pageLoader.getController());

            StackPane content = (StackPane) navigationBar.getParent().getParent().lookup("#content");
            content.getChildren().clear();
            content.getChildren().add(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return page;
    }

    private void loadPage (ObservableList<Node> pageList) {
        Pane content = (StackPane) navigationBar.getParent().getParent().lookup("#content");
        content.getChildren().clear();
        content.getChildren().addAll(pageList);
    }

    private void loadPage (Node page) {
        Pane content = (StackPane) navigationBar.getParent().getParent().lookup("#content");
        content.getChildren().clear();
        content.getChildren().add(page);
    }

    @FXML
    void Logout(ActionEvent event) {

        ApplicationController.clearAllCollections();

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Signin.fxml"));

            Scene scene = new Scene(root);
            Palette.getDefaultPalette().usePalette(scene);

            Stage oldStage = (Stage) navigationBar.getScene().getWindow();
            oldStage.close();

            Stage stage = new Stage(StageStyle.UNIFIED);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Flight Booking Application");
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    AccountPageController openAccount(ActionEvent event) {
        accounBtn.setSelected(true);

        if (accountPages == null ) {
            accountPages = loadPage("/view/accountPage/AccountPage.fxml");
        }
        else {
            loadPage(accountPages);
        }
        return (AccountPageController) accountPages.getUserData();
    }

    @FXML
    void openHome(ActionEvent event) {
        homeBtn.setSelected(true);

        if (homePages.isEmpty()) {
            ApplicationController.homePageStack.push(loadPage("/view/HomePage.fxml"));
        }
        else {
            loadPage(homePages);
        }
    }

    @FXML
    void openSearch(ActionEvent event) {
        searchBtn.setSelected(true);

        if (searchPages.isEmpty()) {
            ApplicationController.searchPageStack.push(loadPage("/view/SearchPage/SearchPage.fxml"));
        }
        else {
            loadPage(searchPages);
        }
    }

    @FXML
    void changeTheme() {
        if (themeButton.isSelected()) {
            Palette.setDefaultPalette(Palette.DarkPalette);
            Palette.getDefaultPalette().usePalette(navigationBar.getScene());
        }
        else {
            Palette.setDefaultPalette(Palette.LightPalette);
            Palette.getDefaultPalette().usePalette(navigationBar.getScene());
        }
    }

    public void refreshSearchPage() {
        ApplicationController.searchPageStack.clear();
        openSearch(new ActionEvent());
    }
    public void refreshHomePage() {
        ApplicationController.homePageStack.clear();
        openHome(new ActionEvent());
    }

    public void pushPage(Node page) {
        if (navigationBarGroup.getSelectedToggle() == homeBtn) {
            ApplicationController.homePageStack.push(page);
        }
        else if (navigationBarGroup.getSelectedToggle() == searchBtn) {
            ApplicationController.searchPageStack.push(page);
        }
    }

    public void popPage() {
        if (navigationBarGroup.getSelectedToggle() == homeBtn) {
            ApplicationController.homePageStack.pop();
        }
        else if (navigationBarGroup.getSelectedToggle() == searchBtn) {
            ApplicationController.searchPageStack.pop();
        }
    }

    public Stack<Node> getCurrentStack() {
        if (navigationBarGroup.getSelectedToggle() == homeBtn) {
            return ApplicationController.homePageStack;
        }
        else if (navigationBarGroup.getSelectedToggle() == searchBtn) {
            return ApplicationController.searchPageStack;
        }
        return null;
    }

    public void openPage() {
        if (navigationBarGroup.getSelectedToggle() == homeBtn) {
            openHome(new ActionEvent());
        }
        else if (navigationBarGroup.getSelectedToggle() == searchBtn) {
            openSearch(new ActionEvent());
        }
    }
}