package data;

import models.Account;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao implements Dao<Account> {

     PassengerDao pdao = new PassengerDao();
     AirlineDao adao = new AirlineDao();

    @Override
    public void create(Account account) {
        Connection conn = DataSource.getConnection();
        String statement = "INSERT INTO accounts (username, password, emailAddress, id_passenger, id_airline) VALUES (?,?,?,?,?);";
        try {
            PreparedStatement query = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            query.setString(1, account.getUsername());
            query.setString(2, account.getPassword());
            query.setString(3, account.getEmailAddress());
            if (account.getPassenger() != null) {
                PassengerDao passengerDao = new PassengerDao();
                passengerDao.create(account.getPassenger());
                query.setInt(4, account.getPassenger().getId());
            }
            if (account.getAirline() != null) {
                AirlineDao airlineDao = new AirlineDao();
                airlineDao.create(account.getAirline());
                query.setInt(5, account.getAirline().getId());
            }

            query.executeUpdate();
            ResultSet id = query.getGeneratedKeys();
            if (id.next()) {
                account.setId(id.getInt(1));
            }
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Account read(int id) {
        Connection conn = DataSource.getConnection();
        Account account = null;
        String statement = "SELECT * FROM accounts WHERE id = ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(1, id);

            ResultSet res = query.executeQuery();

            if (res.next()) {
                account = new Account();
                account.setId(res.getInt("id"));
                account.setUsername(res.getString("username"));
                account.setPassword(res.getString("password"));
                account.setEmailAddress(res.getString("emailAddress"));
                account.setPassenger(pdao.read(res.getInt("id_passenger")));
                account.setAirline(adao.read(res.getInt("id_airline")));
            }

            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }

    public Account read(String username) {
        Connection conn = DataSource.getConnection();
        Account account = null;
        String statement = "SELECT * FROM accounts WHERE username = ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setString(1, username);

            ResultSet res = query.executeQuery();

            if (res.next()) {
                account = new Account();
                account.setId(res.getInt("id"));
                account.setUsername(res.getString("username"));
                account.setPassword(res.getString("password"));
                account.setEmailAddress(res.getString("emailAddress"));
                account.setPassenger(pdao.read(res.getInt("id_passenger")));
                account.setAirline(adao.read(res.getInt("id_airline")));
            }

            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }


    @Override
    public List<Account> readAll() {
        Connection conn = DataSource.getConnection();
        List<Account> list = new ArrayList<Account>();

        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM accounts;");
            ResultSet res = query.executeQuery();
            while (res.next()) {
                Account account = new Account();
                account.setId(res.getInt("id"));
                account.setUsername(res.getString("username"));
                account.setPassword(res.getString("password"));
                account.setEmailAddress(res.getString("emailAddress"));
                account.setPassenger(pdao.read(res.getInt("id_passenger")));
                account.setAirline(adao.read(res.getInt("id_airline")));

                list.add(account);
            }
            return list;
        } catch (SQLException e) {

            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(int id, Account account) {
        Connection conn = DataSource.getConnection();
        Account original =  this.read(id);

        String statement = "UPDATE accounts SET username = ?, password = ?, emailaddress = ? WHERE id = ? ;";

        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(4, id);

            if (account.getUsername() != null) {
                query.setString(1, account.getUsername());
            }
            else {
                query.setString(1, original.getUsername());
            }

            if (account.getPassword() != null) {
                query.setString(2, account.getPassword());
            }
            else {
                query.setString(2, original.getPassword());
            }

            if (account.getEmailAddress() != null) {
                query.setString(3, account.getEmailAddress());
            }
            else {
                query.setString(3, original.getEmailAddress());
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
            PreparedStatement query = conn.prepareStatement("DELETE FROM accounts WHERE id = ? ;");
            query.setInt(1, id);
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

