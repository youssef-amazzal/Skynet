package controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class NavigationBarController implements Initializable {

    @FXML
    private ToggleButton accounBtn;

    @FXML
    private Label appLogo;

    @FXML
    private ToggleButton homeBtn;

    @FXML
    private ToggleButton logoutBtn;

    @FXML
    private VBox navigationBar;

    @FXML
    private ToggleGroup navigationBarGroup;

    @FXML
    private ToggleButton searchBtn;

    @FXML
    private ToggleButton settingsBtn;

    private final ObservableList<Node> searchPages = FXCollections.observableList(ApplicationController.searchPageStack);
    private Node accountPages;
    private Node HomePage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        navigationBar.getStylesheets().add(getClass().getResource("/style/NavigationBar.css").toExternalForm());
        VBox.setVgrow(navigationBar, Priority.ALWAYS);
        alwaysOneSelected();
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
            page = FXMLLoader.load(getClass().getResource(path));
            VBox.setVgrow(page, Priority.ALWAYS);

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

    }

    @FXML
    void openAccount(ActionEvent event) {
        if (accountPages == null ) {
            accountPages = loadPage("/view/accountPage/AccountPage.fxml");
        }
        else {
            loadPage(accountPages);
        }
    }

    @FXML
    void openHome(ActionEvent event) {
        if (HomePage == null) {
            HomePage = loadPage("/view/HomePage.fxml");
        }
        else {
            loadPage(HomePage);
        }
    }

    @FXML
    void openSearch(ActionEvent event) {
        if (searchPages.isEmpty()) {
            ApplicationController.searchPageStack.push(loadPage("/view/SearchPage/SearchPage.fxml"));
        }
        else {
            loadPage(searchPages);
        }
    }

    @FXML
    void openSettings(ActionEvent event) {

    }
}