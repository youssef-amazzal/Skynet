package models;

import data.AccountDao;

import java.time.LocalDate;

public class BankCard {
    private int id;
    private String cardNumber;
    private LocalDate expirationDate;
    private String CVV;
    private int cardHolder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return AccountDao.accountsMap.get(cardHolder);
    }

    public void setCardHolder(int cardHolder) {
        this.cardHolder = cardHolder;
        AccountDao accountDao = new AccountDao();
        accountDao.updateAccountsMap(cardHolder);
    }
}
