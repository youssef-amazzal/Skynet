package models;

import javafx.scene.control.ToggleButton;

import java.time.LocalDate;

public class BankCard extends ToggleButton {
    private int primaryKey;
    private long cardNumber;
    private LocalDate expirationDate;
    private short CVV;
    private Account cardHolder;

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public short getCVV() {
        return CVV;
    }

    public void setCVV(short CVV) {
        this.CVV = CVV;
    }

    public Account getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(Account cardHolder) {
        this.cardHolder = cardHolder;
    }
}
