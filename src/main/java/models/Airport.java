package models;

public class Airport {
    private int id;
    private String name;
    private String city;
    private String country;
    private String IATA;
    private String ICAO;


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

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getIATA() {
        return IATA;
    }
    public void setIATA(String IATA) {
        this.IATA = IATA;
    }

    public String getICAO() {
        return ICAO;
    }
    public void setICAO(String ICAO) {
        this.ICAO = ICAO;
    }

}
