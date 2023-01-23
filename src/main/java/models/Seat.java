package models;

import data.ReservationDao;
import javafx.scene.control.ToggleButton;

public class Seat extends ToggleButton {
    private int PrimaryKey;
    private String column;
    private int row;
    private String type;

    public Seat(){
        this.setMinSize(40, 40);
        this.getStyleClass().add("seat");
    }

    public boolean isReserved(Flight flight) {
        ReservationDao reservationDao = new ReservationDao();
        return reservationDao.read(flight, this) != null;
    }
    public boolean isReservedBy(Account account, Flight flight) {
        ReservationDao reservationDao = new ReservationDao();
        return reservationDao.read(account, flight, this) != null;
    }

    public int getPrimaryKey() {
        return PrimaryKey;
    }
    public void setPrimaryKey(int PrimaryKey) {
        this.PrimaryKey = PrimaryKey;
    }

    public String getColumn() {
        return column;
    }
    public void setColumn(String column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }
    public void setRow(int row) {
        this.row = row;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
        switch (type.toLowerCase()) {
            case "first" -> this.getStyleClass().add("FirstSeatIcon");
            case "business" -> this.getStyleClass().add("BusinessSeatIcon");
            case "economy" -> this.getStyleClass().add("EconomySeatIcon");
        }
    }

}
