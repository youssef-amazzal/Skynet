package models;

import javafx.scene.image.Image;

import java.util.Objects;

public class Airline {
    private int id;
    private String name;
    private String IATA;
    private Image logo;

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

    public String getIATA() {
        return IATA;
    }
    public void setIATA(String IATA) {
        this.IATA = IATA;
    }

    public Image getLogo() {
        if (logo == null) {
            return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/default_logo.png")));
        }
        return logo;
    }
    public void setLogo(Image logo) {
        this.logo = logo;
    }

}
