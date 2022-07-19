package models;

import data.AccountDao;

public class Reservation {
    private int id;
    private Flight flight;
    private int account;
    private Seat seat;
    private int nbrLuggages;
    private double weight;

    public Reservation(Flight flight, int account, Seat seat, int nbrLuggages, double weight) {
        this.flight = flight;
        this.seat = seat;
        this.account = account;
        this.nbrLuggages = nbrLuggages;
        this.weight = weight;
    }

    public Reservation() {
        this.weight = -1;
        this.nbrLuggages = -1;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Account getAccount() {
        return AccountDao.accountsMap.get(account);
    }

    public void setAccount(int account) {
        this.account = account;
        AccountDao accountDao = new AccountDao();
        accountDao.updateAccountsMap(account);
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public int getNbrLuggages() {
        return nbrLuggages;
    }

    public void setNbrLuggages(int nbrLuggages) {
        this.nbrLuggages = nbrLuggages;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
