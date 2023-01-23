package models;

import data.AccountDao;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class CreditCard {
    private int id;
    private final SimpleStringProperty cardHolder;

    private final SimpleStringProperty cardNumber;

    private final SimpleObjectProperty<LocalDate> expirationDate;

    private final SimpleStringProperty CVV;
    private int account;

    public CreditCard() {
        cardHolder = new SimpleStringProperty();
        cardNumber = new SimpleStringProperty();
        expirationDate = new SimpleObjectProperty<>();
        CVV = new SimpleStringProperty();
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return AccountDao.accountsMap.get(account);
    }

    public void setAccount(int account) {
        this.account = account;
        AccountDao accountDao = new AccountDao();
        accountDao.updateAccountsMap(account);
    }

    public String getCardHolder() {
        return cardHolder.get();
    }

    public SimpleStringProperty cardHolderProperty() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder.set(cardHolder);
    }

    public String getCardNumber() {
        return cardNumber.get();
    }

    public String getCardNumberFormatted() {
        return "**** **** **** " + cardNumber.getValue().substring(12);
    }

    public SimpleStringProperty cardNumberProperty() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber.set(cardNumber);
    }

    public LocalDate getExpirationDate() {
        return expirationDate.get();
    }

    public SimpleObjectProperty<LocalDate> expirationDateProperty() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate.set(expirationDate);
    }

    public String getCVV() {
        return CVV.get();
    }

    public SimpleStringProperty CVVProperty() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV.set(CVV);
    }
}
