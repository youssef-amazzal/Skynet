package models;

import data.AirlineDao;
import data.FavoriteDao;
import data.PassengerDao;
import data.ReservationDao;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private static Account currentUser;
    private int id;
    private String username;
    private String password;
    private String emailaddress;
    private int passenger;
    private int airline;

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
        return PassengerDao.passengersMap.get(passenger);
    }
    public void setPassenger(int passenger) {
        this.passenger = passenger;
        PassengerDao passengerDao = new PassengerDao();
        passengerDao.updatePassengersMap(passenger);
    }

    public Airline getAirline() {
        return AirlineDao.airlinesMap.get(airline);
    }
    public void setAirline(int airline) {
        this.airline = airline;
        AirlineDao airlineDao = new AirlineDao();
        airlineDao.updateAirlinesMap(airline);
    }

    public boolean hasReservation(Flight flight) {
        return getReservation(flight) != null;
    }

    public Reservation getReservation(Flight flight) {
        ReservationDao reservationDao = new ReservationDao();
        return reservationDao.read(flight, this);
    }

    public List<Flight> getFavoriteFlights() {
        FavoriteDao favoriteDao = new FavoriteDao();
        List<Favorite> favoriteList = favoriteDao.readAll();
        List<Flight> favoriteFlights = new ArrayList<>();
        favoriteList.forEach(favorite -> favoriteFlights.add(favorite.getFlight()));
        return favoriteFlights;
    }

}
