package models;

import java.time.LocalDate;

public class BankCard {
    private int primaryKey;
    private String cardNumber;
    private LocalDate expirationDate;
    private String CVV;
    private Account cardHolder;

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardNumberFormatted() {
        return "**** **** **** " + cardNumber.substring(12);
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCVV() {
        return CVV;
    }

    public void setCVV(String CVV) {
        this.CVV = CVV;
    }

    public Account getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(Account cardHolder) {
        this.cardHolder = cardHolder;
    }
}
