package models;

import data.AccountDao;

import java.time.LocalDate;

public class BankCard {
    private int id;
    private  String cardHolder;

    private String cardNumber;

    private LocalDate expirationDate;
    private String CVV;
    private int account;
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

    public Account getAccount() {
        return AccountDao.accountsMap.get(account);
    }

    public void setAccount(int account) {
        this.account = account;
        AccountDao accountDao = new AccountDao();
        accountDao.updateAccountsMap(account);
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }
}
