package models;

import java.sql.Date;

public class Seat {
    private int id;
    private String column;
    private Int row;
    private String type;



    public Seat() {
        this.column = null;
        this.row = null;
        this.type = null;

    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getColumn() {
        return column;
    }
    public void setColumn(String Column) {
        this.column = column;
    }

    public Int getRow() {
        return row;
    }
    public void setRow(Int row) {
        this.row = row;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

}
