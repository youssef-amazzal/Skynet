package data;

import models.Account;
import models.Favorite;
import models.Flight;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FavoriteDao implements Dao<Favorite> {

    public static final HashMap<Integer, Favorite> favoritesMap = new HashMap<>();

    FlightDao flightDao = FlightDao.getInstance();

    @Override
    public int create(Favorite favorite) {
        Connection conn = DataSource.getConnection();
        String statement = "INSERT INTO favorites (id_flight, id_account) VALUES (?,?);";
        try {
            PreparedStatement query = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            query.setInt(1, favorite.getFlight().getId());
            query.setInt(2, favorite.getAccount().getId());

            query.executeUpdate();
            ResultSet id = query.getGeneratedKeys();
            if (id.next()) {
                favorite.setId(id.getInt(1));
                favoritesMap.put(favorite.getFlight().getId(), favorite);
            }
            query.close();
            return id.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Favorite read(int id) {
        Connection conn = DataSource.getConnection();
        Favorite favorite = null;
        String statement = "SELECT * FROM favorites WHERE id = ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(1, id);

            ResultSet res = query.executeQuery();
            if (res.next()) {
                favorite = new Favorite();
                favorite.setId(res.getInt("id"));
                favorite.setFlight(flightDao.read(res.getInt("id_flight")));
                favorite.setAccount((res.getInt("id_account")));
                favoritesMap.put(res.getInt("id_flight"), favorite);
            }

            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favorite;
    }

    public Favorite read(Flight flight, Account account) {

        Favorite favorite = favoritesMap.get(flight.getId());
        if (favorite != null) {
            return favorite;
        }

        Connection conn = DataSource.getConnection();
        String statement = "SELECT * FROM favorites WHERE id_flight = ? AND id_account = ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(1, flight.getId());
            query.setInt(2, account.getId());

            ResultSet res = query.executeQuery();

            if (res.next()) {
                favorite = new Favorite();
                favorite.setId(res.getInt("id"));
                favorite.setFlight(flightDao.read(res.getInt("id_flight")));
                favorite.setAccount((res.getInt("id_account")));
                favoritesMap.put(res.getInt("id_flight"), favorite);
            }

            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favorite;
    }

    @Override
    public List<Favorite> readAll() {
        Connection conn = DataSource.getConnection();
        List<Favorite> list = new ArrayList<>();

        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM favorites;");
            ResultSet res = query.executeQuery();
            while (res.next()) {
                Favorite favorite = new Favorite();
                favorite.setId(res.getInt("id"));
                favorite.setFlight(flightDao.read(res.getInt("id_flight")));
                favorite.setAccount((res.getInt("id_account")));

                list.add(favorite);
                favoritesMap.put(res.getInt("id_flight"), favorite);
            }
            return list;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public List<Favorite> readAll(Account account) {
        Connection conn = DataSource.getConnection();
        List<Favorite> list = new ArrayList<>();

        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM favorites WHERE id_account = ?;");
            query.setInt(1, account.getId());
            ResultSet res = query.executeQuery();

            while (res.next()) {
                Favorite favorite = new Favorite();
                favorite.setId(res.getInt("id"));
                favorite.setFlight(flightDao.read(res.getInt("id_flight")));
                favorite.setAccount((res.getInt("id_account")));

                list.add(favorite);
                favoritesMap.put(res.getInt("id_flight"), favorite);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(int id, Favorite favorite) {
        Connection conn = DataSource.getConnection();
        Favorite original =  this.read(id);

        String statement = "UPDATE favorites SET id_flight = ?, id_account = ? WHERE id = ? ;";

        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(3, id);

            if (favorite.getFlight() != null) {
                query.setInt(1, favorite.getFlight().getId());
            }
            else {
                query.setInt(1, original.getFlight().getId());
            }

            if (favorite.getAccount() != null) {
                query.setInt(2, favorite.getAccount().getId());
            }
            else {
                query.setInt(2, original.getAccount().getId());
            }

            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        Favorite favorite = read(id);
        Connection conn = DataSource.getConnection();
        try {
            PreparedStatement query = conn.prepareStatement("DELETE FROM favorites WHERE id = ? ;");
            query.setInt(1, id);
            query.executeUpdate();
            query.close();
            FavoriteDao.favoritesMap.remove(favorite.getFlight().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
