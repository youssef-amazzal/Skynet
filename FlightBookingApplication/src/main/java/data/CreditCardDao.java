package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Account;
import models.CreditCard;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CreditCardDao implements Dao<CreditCard> {

    private static ObservableList<CreditCard> cardList;

    public ObservableList<CreditCard> getCardList() {
        if (cardList == null) {
            cardList = FXCollections.observableList(read(Account.getCurrentUser()));
        }
        return cardList;
    }
    public static void deleteCardList() {
        cardList = null;
    }

    @Override
    public int create(CreditCard creditCard) {
        Connection conn = DataSource.getConnection();
        String statement = "INSERT INTO creditCards (cardNumber, expirationDate, CVV, id_account, cardHolder) VALUES (?,?,?,?,?);";
        try {
            PreparedStatement query = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            query.setString(1, creditCard.getCardNumber());
            query.setString(2, creditCard.getExpirationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            query.setString(3, creditCard.getCVV());
            query.setInt(4, creditCard.getAccount().getId());
            query.setString(5, creditCard.getCardHolder());


            query.executeUpdate();
            ResultSet id = query.getGeneratedKeys();
            if (id.next()) {
                creditCard.setId(id.getInt(1));
            }
            query.close();
            return id.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public CreditCard read(int id) {
        Connection conn = DataSource.getConnection();
        CreditCard creditCard = null;
        String statement = "SELECT * FROM creditCards WHERE id = ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(1, id);

            ResultSet res = query.executeQuery();

            if (res.next()) {
                creditCard = new CreditCard();
                creditCard.setId(res.getInt("id"));
                creditCard.setCardNumber(res.getString("cardNumber"));
                creditCard.setExpirationDate(LocalDate.parse(res.getString("expirationDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                creditCard.setCVV(res.getString("CVV"));
                creditCard.setAccount(res.getInt("id_account"));
                creditCard.setCardHolder(res.getString("cardHolder"));

            }

            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return creditCard;
    }

    public List<CreditCard> read(Account id_account) {
        Connection conn = DataSource.getConnection();
        List<CreditCard> list = new ArrayList<>();

        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM creditCards Where id_account = ?;");
            query.setInt(1, id_account.getId());
            ResultSet res = query.executeQuery();

            while (res.next()) {
                CreditCard creditCard = new CreditCard();
                creditCard.setId(res.getInt("id"));
                creditCard.setCardNumber(res.getString("cardNumber"));
                creditCard.setExpirationDate(LocalDate.parse(res.getString("expirationDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                creditCard.setCVV(res.getString("CVV"));
                creditCard.setAccount(res.getInt("id_account"));
                creditCard.setCardHolder(res.getString("cardHolder"));

                list.add(creditCard);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<CreditCard> readAll() {
        Connection conn = DataSource.getConnection();
        List<CreditCard> list = new ArrayList<>();

        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM creditCards;");
            ResultSet res = query.executeQuery();
            while (res.next()) {
                CreditCard creditCard = new CreditCard();
                creditCard.setId(res.getInt("id"));
                creditCard.setCardNumber(res.getString("cardNumber"));
                creditCard.setExpirationDate(LocalDate.parse(res.getString("expirationDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                creditCard.setCVV(res.getString("CVV"));
                creditCard.setAccount(res.getInt("id_account"));
                creditCard.setCardHolder(res.getString("cardHolder"));

                list.add(creditCard);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(int id, CreditCard creditCard) {
        Connection conn = DataSource.getConnection();
        CreditCard original =  this.read(id);

        String statement = "UPDATE creditCards SET cardNumber = ?, expirationDate = ?, CVV = ?, cardHolder = ? WHERE id = ? ;";

        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(5, id);

            if (creditCard.getCardNumber() != null) {
                query.setString(1, creditCard.getCardNumber());
            }
            else {
                query.setString(1, original.getCardNumber());
            }

            if (creditCard.getExpirationDate() != null) {
                query.setString(2,  creditCard.getExpirationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }
            else {
                query.setString(2,  creditCard.getExpirationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }

            if (creditCard.getCVV() != null) {
                query.setString(3, creditCard.getCVV());
            }
            else {
                query.setString(3, original.getCVV());
            }

            if (creditCard.getCardHolder() != null) {
                query.setString(4, creditCard.getCardHolder());
            }
            else {
                query.setString(4, original.getCardHolder());
            }

            query.executeUpdate();
            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        Connection conn = DataSource.getConnection();
        try {
            PreparedStatement query = conn.prepareStatement("DELETE FROM creditCards WHERE id = ? ;");
            query.setInt(1, id);
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
