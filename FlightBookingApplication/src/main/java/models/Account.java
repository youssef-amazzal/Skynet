package models;

import java.sql.Date;

public class Account {
    private int id;
    private String username;
    private String password;
    private String emailadress;
    private Passenger id_passenger;
    private Airline id_airline;


    public Account() {
        this.username = null;
        this.password= null;
        this.emailadress = null;

    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAdress() {
        return emialadress;
    }
    public void setEmailAdress(String emailadress) {
        this.emailadress = emailadress;
    }

    public Passenger getid_passenger() {
        return id_passenger;
    }
    public void setid_passenger(Passenger id_passenger) {
        this.id_passenger = id_passenger;
    }

    public Airline getid_airline() {
        return id_airline;
    }
    public void setid_airline(Airline id_airline) {
        this.id_airline = id_airline;
    }


}
