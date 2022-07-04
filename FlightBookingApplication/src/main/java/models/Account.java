package models;

import java.sql.Date;

public class Account {
    private static Account currentUser;
    private int id;
    private String username;
    private String password;
    private String emailaddress;
    private Passenger passenger;
    private Airline airline;

    public static Account getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Account currentUser) {
        Account.currentUser = currentUser;
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

    public String getEmailAddress() {
        return emailaddress;
    }
    public void setEmailAddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public Passenger getPassenger() {
        return passenger;
    }
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Airline getAirline() {
        return airline;
    }
    public void setAirline(Airline airline) {
        this.airline = airline;
    }


}
