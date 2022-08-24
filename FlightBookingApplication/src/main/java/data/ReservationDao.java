package data;

import models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReservationDao implements Dao<Reservation> {
    FlightDao flightDao = FlightDao.getInstance();
    SeatDao seatDao = new SeatDao();
    public int countReservations(Flight flight) {
        Connection conn = DataSource.getConnection();
        String statement = "SELECT COUNT(reservations.id) AS total FROM reservations WHERE id_flight = ?;";
        int total = 0;
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(1, flight.getId());

            ResultSet res = query.executeQuery();

            if (res.next()) {
                total = res.getInt("total");
            }

            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }


    @Override
    public int create(Reservation reservation) {
        Connection conn = DataSource.getConnection();
        String statement = "INSERT INTO reservations (id_flight, id_account, id_seat, nbr_luggages, weight) VALUES (?,?,?,?,?);";
        try {
            PreparedStatement query = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            query.setInt(1, reservation.getFlight().getId());
            query.setInt(2, reservation.getAccount().getId());
            query.setInt(3,reservation.getSeat().getPrimaryKey());
            query.setInt(4,reservation.getNbrLuggages());
            query.setDouble(5,reservation.getWeight());

            query.executeUpdate();
            ResultSet id = query.getGeneratedKeys();
            if (id.next()) {
                reservation.setId(id.getInt(1));
            }
            query.close();
            return id.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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
                reservation.setAccount((res.getInt("id_account")));
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

    public List<Reservation> read(Account account) {
        Connection conn = DataSource.getConnection();
        List<Reservation> list = new ArrayList<>();
        String statement = "SELECT * FROM reservations WHERE id_account = ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(1, account.getId());
            ResultSet res = query.executeQuery();
            while (res.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(res.getInt("id"));
                reservation.setFlight(flightDao.read(res.getInt("id_flight")));
                reservation.setAccount((res.getInt("id_account")));
                reservation.setSeat(seatDao.read(res.getInt("id_seat")));
                reservation.setNbrLuggages(res.getInt("nbr_luggages"));
                reservation.setWeight(res.getInt("weight"));

                list.add(reservation);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Reservation read(Flight flight, Seat seat) {
        Connection conn = DataSource.getConnection();
        Reservation reservation = null;
        String statement = "SELECT * FROM reservations WHERE id_flight = ? AND id_seat = ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(1, flight.getId());
            query.setInt(2, seat.getPrimaryKey());

            ResultSet res = query.executeQuery();

            if (res.next()) {
                reservation = new Reservation();
                reservation.setId(res.getInt("id"));
                reservation.setFlight(flightDao.read(res.getInt("id_flight")));
                reservation.setAccount((res.getInt("id_account")));
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

    public Reservation read(Account account, Flight flight, Seat seat) {
        Connection conn = DataSource.getConnection();
        Reservation reservation = null;
        String statement = "SELECT * FROM reservations WHERE id_flight = ? AND id_seat = ? AND id_account = ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(1, flight.getId());
            query.setInt(2, seat.getPrimaryKey());
            query.setInt(3, account.getId());

            ResultSet res = query.executeQuery();

            if (res.next()) {
                reservation = new Reservation();
                reservation.setId(res.getInt("id"));
                reservation.setFlight(flightDao.read(res.getInt("id_flight")));
                reservation.setAccount((res.getInt("id_account")));
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

    public Reservation read(Flight flight, Account account) {
        Connection conn = DataSource.getConnection();
        Reservation reservation = null;
        String statement = "SELECT * FROM reservations WHERE id_flight = ? AND id_account = ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(1, flight.getId());
            query.setInt(2, account.getId());

            ResultSet res = query.executeQuery();

            if (res.next()) {
                reservation = new Reservation();
                reservation.setId(res.getInt("id"));
                reservation.setFlight(flightDao.read(res.getInt("id_flight")));
                reservation.setAccount((res.getInt("id_account")));
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
        List<Reservation> list = new ArrayList<>();

        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM reservations;");
            ResultSet res = query.executeQuery();
            while (res.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(res.getInt("id"));
                reservation.setFlight(flightDao.read(res.getInt("id_flight")));
                reservation.setAccount((res.getInt("id_account")));
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
                query.setInt(3, reservation.getSeat().getPrimaryKey());
            }
            else {
                query.setInt(3, original.getSeat().getPrimaryKey());
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
