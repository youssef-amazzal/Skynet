package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Account;
import models.Flight;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FlightDao implements Dao<Flight> {
    private static FlightDao flightDao;
    private static ObservableList<Flight> fightsList;

    private FlightDao() {
    }

    public static FlightDao getInstance() {
        if (flightDao == null) {
            flightDao = new FlightDao();
        }
        return flightDao;
    }

    public ObservableList<Flight> getFlightsList() {
        if (fightsList == null) {
            fightsList = FXCollections.observableList(this.readAll());
        }
        return  fightsList;
    }

    AirportDao airportDao = new AirportDao();
    @Override
    public int create(Flight flight) {
        Connection conn = DataSource.getConnection();
        String statement = "INSERT INTO flights (dep_datetime, arr_datetime, first_price, business_price, economy_price, luggage_price, weight_price, id_airline, dep_airport, arr_airport) VALUES (?,?,?,?,?,?,?,?,?,?);";
        try {
            PreparedStatement query = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            query.setLong(1, flight.getDepDatetime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            query.setLong(2, flight.getArrDatetime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            query.setDouble(3, flight.getFirstPrice());
            query.setDouble(4, flight.getBusinessPrice());
            query.setDouble(5, flight.getEconomyPrice());
            query.setDouble(6, flight.getLuggagePrice());
            query.setDouble(7, flight.getWeightPrice());
            query.setInt(8, flight.getAirline().getId());
            query.setInt(9, flight.getDepAirport().getId());
            query.setInt(10, flight.getArrAirport().getId());
            query.executeUpdate();
            ResultSet id = query.getGeneratedKeys();
            if (id.next()) {
                flight.setId(id.getInt(1));
            }
            query.close();
            return id.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Flight read(int id) {
        Connection conn = DataSource.getConnection();
        Flight flight = null;
        String statement = "SELECT * FROM flights WHERE id = ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(1, id);

            ResultSet res = query.executeQuery();

            if (res.next()) {
                flight = new Flight();
                flight.setId(res.getInt("id"));
                flight.setDepDatetime(res.getTimestamp("dep_datetime").toLocalDateTime());
                flight.setArrDatetime(res.getTimestamp("arr_datetime").toLocalDateTime());
                flight.setFirstPrice(res.getDouble("first_price"));
                flight.setBusinessPrice(res.getDouble("business_price"));
                flight.setEconomyPrice(res.getDouble("economy_price"));
                flight.setLuggagePrice(res.getDouble("luggage_price"));
                flight.setWeightPrice(res.getDouble("weight_price"));
                flight.setAirline(res.getInt("id_airline"));
                flight.setDepAirport(airportDao.read(res.getInt("dep_airport")));
		        flight.setArrAirport(airportDao.read(res.getInt("arr_airport")));
            }

            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flight;
    }

    public List<Flight> readUpComing() {
        Connection conn = DataSource.getConnection();
        List<Flight> list = new ArrayList<Flight>();

        try {
            PreparedStatement query = conn.prepareStatement("" +
                    "SELECT * FROM flights WHERE id IN ("
                        + "SELECT id_flight FROM reservations where id_account = ?);"
            );
            query.setInt(1, Account.getCurrentUser().getId());
            ResultSet res = query.executeQuery();
            while (res.next()) {
                Flight flight = new Flight();
                flight.setId(res.getInt("id"));
                flight.setDepDatetime(res.getTimestamp("dep_datetime").toLocalDateTime());
                flight.setArrDatetime(res.getTimestamp("arr_datetime").toLocalDateTime());
                flight.setFirstPrice(res.getDouble("first_price"));
                flight.setBusinessPrice(res.getDouble("business_price"));
                flight.setEconomyPrice(res.getDouble("economy_price"));
                flight.setLuggagePrice(res.getDouble("luggage_price"));
                flight.setWeightPrice(res.getDouble("weight_price"));
                flight.setAirline(res.getInt("id_airline"));
                flight.setDepAirport(airportDao.read(res.getInt("dep_airport")));
                flight.setArrAirport(airportDao.read(res.getInt("arr_airport")));


                list.add(flight);
            }
            return list;
        } catch (SQLException e) {

            e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<Flight> readAll() {
        Connection conn = DataSource.getConnection();
        LinkedList<Flight> list = new LinkedList<>();

        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM flights;");
            ResultSet res = query.executeQuery();
            while (res.next()) {
                Flight flight = new Flight();
                flight.setId(res.getInt("id"));
                flight.setDepDatetime(res.getTimestamp("dep_datetime").toLocalDateTime());
                flight.setArrDatetime(res.getTimestamp("arr_datetime").toLocalDateTime());
                flight.setFirstPrice(res.getDouble("first_price"));
                flight.setBusinessPrice(res.getDouble("business_price"));
                flight.setEconomyPrice(res.getDouble("economy_price"));
                flight.setLuggagePrice(res.getDouble("luggage_price"));
                flight.setWeightPrice(res.getDouble("weight_price"));
                flight.setAirline(res.getInt("id_airline"));
                flight.setDepAirport(airportDao.read(res.getInt("dep_airport")));
                flight.setArrAirport(airportDao.read(res.getInt("arr_airport")));
              

                list.addFirst(flight);
            }
            return list;
        } catch (SQLException e) {

            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(int id, Flight flight) {
        Connection conn = DataSource.getConnection();
        Flight original =  this.read(id);

        String statement = "UPDATE flights SET dep_datetime= ?, arr_datetime= ?, first_price= ?, business_price= ?, economy_price= ?, luggage_price= ?, weight_price= ?, id_airline= ?, dep_airport= ?, arr_airport= ? WHERE id = ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(11, id);

            if (flight.getDepDatetime() != null) {
                query.setLong(1, flight.getDepDatetime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }
            else {
                query.setLong(1, original.getDepDatetime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }

            if (flight.getArrDatetime() != null) {
                query.setLong(2, flight.getArrDatetime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }
            else {
                query.setLong(2, original.getArrDatetime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            }

            if (flight.getFirstPrice() != -1) {
                query.setDouble(3, flight.getFirstPrice());
            }
            else {
                query.setDouble(3, original.getFirstPrice());
            }

            if (flight.getBusinessPrice() != -1) {
                query.setDouble(4, flight.getBusinessPrice());
            }
            else {
                query.setDouble(4, original.getBusinessPrice());
            }
            
            if (flight.getEconomyPrice() != -1) {
                query.setDouble(5, flight.getEconomyPrice());
            }
            else {
                query.setDouble(5, original.getEconomyPrice());
            }

            if (flight.getLuggagePrice() != -1) {
                query.setDouble(6, flight.getLuggagePrice());
            }
            else {
                query.setDouble(6, original.getLuggagePrice());
            }

            if (flight.getWeightPrice() != -1) {
                query.setDouble(7, flight.getWeightPrice());
            }
            else {
                query.setDouble(7, original.getWeightPrice());
            }

            if (flight.getAirline() != null) {
                query.setInt(8, flight.getAirline().getId());
            }
            else {
                query.setInt(8, original.getAirline().getId());
            }

            if (flight.getDepAirport() != null) {
                query.setInt(9, flight.getDepAirport().getId());
            }
            else {
                query.setInt(9, original.getDepAirport().getId());
            }

            if (flight.getArrAirport() != null) {
                query.setInt(10, flight.getArrAirport().getId());
            }
            else {
                query.setInt(10, original.getArrAirport().getId());
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
            PreparedStatement query = conn.prepareStatement("DELETE FROM flights WHERE id = ? ;");
            query.setInt(1, id);
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
