package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class SeatMapController implements Initializable {

    @FXML
    private HBox confirmationWindow;

    @FXML
    private GridPane seatMap;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int row = 0;
        int col = 0;
        char ref = 'A';
        for (int i = 1; i <= 6; i++) {
            if (col == 3) {i--; col++; continue;}
            Label lbl = new Label(Character.toString(ref++));
            lbl.setMinSize(40, 40);
            lbl.setAlignment(Pos.CENTER);
            seatMap.add(lbl, col++, 0);
        }
        col = 0;
        row = 1;

        ToggleGroup seatGroup = new ToggleGroup();

        for (int i = 1; i <= 20 * 6; i++) {
            if (col == 3) {
                Label lbl = new Label(row + "");
                lbl.setMinSize(40, 40);
                lbl.setAlignment(Pos.CENTER);
                seatMap.add(lbl, col++, row);
                i--;
                continue;
            }
            if (row == 8 || row == 12) {
                Label lbl1 = new Label("EXIT");
                Label lbl2 = new Label("EXIT");
                lbl1.setMinSize(40, 40);
                lbl2.setMinSize(40, 40);
                lbl2.setAlignment(Pos.BASELINE_RIGHT);
                seatMap.add(lbl1, 0, row);
                seatMap.add(lbl2, 6, row++);
            }

            ToggleButton seat = new ToggleButton();
            seat.setMinSize(40, 40);
            seat.setToggleGroup(seatGroup);
            seat.getStyleClass().add("seat");

            seatMap.add(seat, col++, row);
            if (col == 7) {
                col = 0;
                row++;
            }

        }
    }
}
