package models;

import java.sql.Date;

public class Airline {
    private int id;
    private String name;
    private String logo;


    public Airline() {
        this.name = null;
        this.logo = null;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }
    public void setLogo(String logo) {
        this.logo = logo;
    }

}
