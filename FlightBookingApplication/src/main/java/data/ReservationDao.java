package data;

import models.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDao implements Dao<Reservation> {
    AccountDao accountDao = new AccountDao();
    FlightDao flightDao = new FlightDao();
    SeatDao seatDao = new SeatDao();


    @Override
    public void create(Reservation reservation) {
        Connection conn = DataSource.getConnection();
        String statement = "INSERT INTO reservations (id_flight, id_account, id_seat, nbr_luggages, weight) VALUES (?,?,?,?,?);";
        try {
            PreparedStatement query = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            query.setInt(1, reservation.getFlight().getId());
            query.setInt(2, reservation.getAccount().getId());
            query.setInt(3,reservation.getSeat().getId());
            query.setInt(4,reservation.getNbrLuggages());
            query.setDouble(5,reservation.getWeight());

            query.executeUpdate();
            ResultSet id = query.getGeneratedKeys();
            if (id.next()) {
                reservation.setId(id.getInt(1));
            }
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reservation read(int id) {
        Connection conn = DataSource.getConnection();
        Reservation reservation = null;
        String statement = "SELECT * FROM reservations WHERE id = ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(1, id);

            ResultSet res = query.executeQuery();

            if (res.next()) {
                reservation = new Reservation();
                reservation.setId(res.getInt("id"));
                reservation.setFlight(flightDao.read(res.getInt("id_flight")));
                reservation.setAccount(accountDao.read(res.getInt("id_account")));
                reservation.setSeat(seatDao.read(res.getInt("id_seat")));
                reservation.setNbrLuggages(res.getInt("nbr_luggages"));
                reservation.setWeight(res.getInt("weight"));
            }

            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservation;
    }


    @Override
    public List<Reservation> readAll() {
        Connection conn = DataSource.getConnection();
        List<Reservation> list = new ArrayList<Reservation>();

        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM reservations;");
            ResultSet res = query.executeQuery();
            while (res.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(res.getInt("id"));
                reservation.setFlight(flightDao.read(res.getInt("id_flight")));
                reservation.setAccount(accountDao.read(res.getInt("id_account")));
                reservation.setSeat(seatDao.read(res.getInt("id_seat")));
                reservation.setNbrLuggages(res.getInt("nbr_luggages"));
                reservation.setWeight(res.getInt("weight"));

                list.add(reservation);
            }
            return list;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(int id, Reservation reservation) {
        Connection conn = DataSource.getConnection();
        Reservation original =  this.read(id);

        String statement = "UPDATE reservations SET id_flight = ?, id_account = ?, id_seat = ?, nbr_luggages = ?, weight = ? WHERE id = ? ;";

        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(6, id);

            if (reservation.getFlight() != null) {
                query.setInt(1, reservation.getFlight().getId());
            }
            else {
                query.setInt(1, original.getFlight().getId());
            }

            if (reservation.getAccount() != null) {
                query.setInt(2, reservation.getAccount().getId());
            }
            else {
                query.setInt(2, original.getAccount().getId());
            }

            if (reservation.getSeat() != null) {
                query.setInt(3, reservation.getSeat().getId());
            }
            else {
                query.setInt(3, original.getSeat().getId());
            }

            if (reservation.getNbrLuggages() != -1) {
                query.setInt(4, reservation.getNbrLuggages());
            }
            else {
                query.setInt(4, original.getNbrLuggages());
            }

            if (reservation.getWeight() != -1) {
                query.setDouble(5, reservation.getWeight());
            }
            else {
                query.setDouble(5, original.getWeight());
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
            PreparedStatement query = conn.prepareStatement("DELETE FROM reservations WHERE id = ? ;");
            query.setInt(1, id);
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
