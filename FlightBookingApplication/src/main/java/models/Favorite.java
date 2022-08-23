package models;

import data.AccountDao;

public class Favorite {
    private int id;
    private Flight flight;
    private int account;

    public Favorite(Flight flight, Account account) {
        this.flight = flight;
        this.account = account.getId();
    }

    public Favorite() {
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
    }
}
