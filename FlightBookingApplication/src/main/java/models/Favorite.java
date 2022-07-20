package models;

import data.AccountDao;
import javafx.beans.property.SimpleBooleanProperty;

public class Favorite {
    private int id;
    private final SimpleBooleanProperty isFavorite;
    private Flight flight;
    private int account;

    public Favorite(Flight flight, Account account, boolean isFavorite) {
        this.isFavorite = new SimpleBooleanProperty(isFavorite);
        this.flight = flight;
        this.account = account.getId();
    }

    public Favorite() {
        isFavorite = new SimpleBooleanProperty(true);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite.set(isFavorite);
    }

    public SimpleBooleanProperty getIsFavoriteProperty() {
        return isFavorite;
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
