package data;

import java.sql.*;
import java.util.*;

import models.Flight;
import models.Airline;
import models.Airport;

public class FlightDao implements Dao<Flight> {
		AirlineDao adao = new AirlineDao();
		AirportDao aiDao = new AirportDao();
    @Override
    public void create(Flight flight) {
        Connection conn = DataSource.getConnection();
        String statement = "INSERT INTO flights (dep_datetime, arr_datetime, first_price, business_price, economy_price, luggage_price, weight_price) VALUES (?,?,?,?,?,?,?);";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setDouble(1, flight.getDep_Datetime());
            query.setDouble(2, flight.getArr_Datetime());
            query.setDouble(3, flight.getFirst_Price());
            query.setDouble(4, flight.getBusiness_Price());
            query.setDouble(5, flight.getEconomy_Price());
            query.setDouble(6, flight.getLuggage_Price());
            query.setDouble(7, flight.getWeight_Price());
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
                flight.setDep_Datetime(res.getString("dep_datetile"));
                flight.setArr_Dateime(res.getString("arr_datetime"));
                flight.setFirst_Price(res.getString("first_price"));
                flight.setBusiness_Price(res.getString("business_price"));
                flight.setEconomy_Price(res.getString("economy_price"));
                flight.setLuggage_Price(res.getString("luggage_price"));
                flight.setWeight_Price(res.getString("weigh_price"));
                flight.setAirline(adao.read(res.getInt("id_airline")));
                flight.setDep_Airport(aidao.read(res.getInt("dep_airport")));
		        flight.setArr_Airport(aidao.read(res.getInt("arr_airport")));
            }

            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flight;
    }


    @Override
    public List<Flight> readAll() {
        Connection conn = DataSource.getConnection();
        List<Flight> list = new ArrayList<Flight>();

        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM flights;");
            ResultSet res = query.executeQuery();
            while (res.next()) {
                Flight flight = new Flight();
                flight.setId(res.getInt("id")); 
                flight.setDep_Datetime(res.getString("dep_datetile"));
                flight.setArr_Dateime(res.getString("arr_datetime"));
                flight.setFirst_Price(res.getString("first_price"));
                flight.setBusiness_Price(res.getString("business_price"));
                flight.setEconomy_Price(res.getString("economy_price"));
                flight.setLuggage_Price(res.getString("luggage_price"));
                flight.setWeight_Price(res.getString("weigh_price"));
                flight.setAirline(adao.read(res.getInt("id_airline")));
                flight.setDep_Airport(aidao.read(res.getInt("dep_airport")));
		        flight.setArr_Airport(aidao.read(res.getInt("arr_airport")));
              

                list.add(flight);
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

        String statement = "UPDATE flight SET dep_datetime= ?, arr_datetime= ?, first_price= ?, business_price= ?, economy_price= ?, luggage_price= ?, weight_price= ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(7, id);

            if (flight.getDep_Datetime() != null) {
                query.setString(1, flight.getDep_Datetime());
            }
            else {
                query.setString(1, original.getDep_Datetime());
            }

            if (aflight.getArr_Datetime() != null) {
                query.setString(2, flight.getArr_Datetime());
            }
            else {
                query.setString(2, original.getArr_Datetime());
            }

            if (flight.getFirst_Price() != null) {
                query.setString(3, flight.getFirst_Price());
            }
            else {
                query.setString(3, original.getFirst_Price());
            }

            if (flight.getBusiness_Price() != null) {
                query.setString(4, flight.getBusiness_Price());
            }
            else {
                query.setString(4, original.getBusiness_Price());
            }
            
            if (filght.getEconomy_Price() != null) {
                query.setString(5, flight.getEconomy_Price());
            }
            else {
                query.setString(5, original.getEconomy_Price());
            }

            if (filght.getLuggage_Price() != null) {
                query.setString(6, flight.getLuggage_Price());
            }
            else {
                query.setString(6, original.getLuggage_Price());
            }

            if (filght.getWeight_Price() != null) {
                query.setString(7, flight.getWeight_Price());
            }
            else {
                query.setString(7, original.getWeight_Price());
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
            query.setDouble(2, dep_datetime);
            query.setDouble(3, arr_datetime);
            query.setDouble(4, first_price);
            query.setDouble(5, business_price); 
            query.setDouble(6, economy_price); 
            query.setDouble(7, luggage_price); 
            query.setDouble(8, weight_price);    
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
