package data;

import java.sql.*;
import java.util.*;

import models.Airport;

public class AirportDao implements Dao<Airport> {

    @Override
    public void create(Airport airport) {
        Connection conn = DataSource.getConnection();
        String statement = "INSERT INTO airports (name, city, country, IATA, ICAO) VALUES (?,?,?,?,?);";
        try {
            PreparedStatement query = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            query.setString(1, airport.getName());
            query.setString(2, airport.getCity());
            query.setString(3, airport.getCountry());
            query.setString(4, airport.getIATA());
            query.setString(5, airport.getICAO());

            query.executeUpdate();
            ResultSet id = query.getGeneratedKeys();
            if (id.next()) {
                airport.setId(id.getInt(1));
            }
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Airport read(int id) {
        Connection conn = DataSource.getConnection();
        Airport airport = null;
        String statement = "SELECT * FROM airports WHERE id = ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(1, id);

            ResultSet res = query.executeQuery();

            if (res.next()) {
                airport = new Airport();
                airport .setId(res.getInt("id"));
                airport .setName(res.getString("name"));
                airport .setCity(res.getString("city"));
                airport .setCountry(res.getString("country"));
                airport .setIATA(res.getString("IATA"));
                airport .setICAO(res.getString("ICAO"));
                
            }

            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return airport;
    }


    @Override
    public List<Airport> readAll() {
        Connection conn = DataSource.getConnection();
        List<Airport> list = new ArrayList<Airport>();

        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM airports;");
            ResultSet res = query.executeQuery();
            while (res.next()) {
                Airport airport = new Airport();
                airport.setId(res.getInt("id"));
                airport.setName(res.getString("name"));
                airport.setCity(res.getString("city"));
                airport.setCountry(res.getString("country"));
                airport.setIATA(res.getString("IATA"));
                airport.setICAO(res.getString("ICAO"));
              

                list.add(airport);
            }
            return list;
        } catch (SQLException e) {
           
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(int id, Airport airport) {
        Connection conn = DataSource.getConnection();
        Airport original =  this.read(id);

        String statement = "UPDATE airports SET name = ?, city = ?, country = ?, IATA = ?, ICAO = ? WHERE id = ? ;";

        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(6, id);

            if (airport.getName() != null) {
                query.setString(1, airport.getName());
            }
            else {
                query.setString(1, original.getName());
            }

            if (airport.getCity() != null) {
                query.setString(2, airport.getCity());
            }
            else {
                query.setString(2, original.getCity());
            }

            if (airport.getCountry() != null) {
                query.setString(3, airport.getCountry());
            }
            else {
                query.setString(3, original.getCountry());
            }

            if (airport.getIATA() != null) {
                query.setString(4, airport.getIATA());
            }
            else {
                query.setString(4, original.getIATA());
            }
            
            if (airport.getICAO() != null) {
                query.setString(5, airport.getICAO());
            }
            else {
                query.setString(5, original.getICAO());
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
            PreparedStatement query = conn.prepareStatement("DELETE FROM airports WHERE id = ? ;");
            query.setInt(1, id);
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
