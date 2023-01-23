package data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Airport;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AirportDao implements Dao<Airport> {
    private static ObservableList<String> cityList;
    private static ObservableList<String> countryList;

    public static ObservableList<String> getCityList() {
        if (cityList == null) {
            cityList = FXCollections.observableList(new LinkedList<>());
            Connection conn = DataSource.getConnection();
            String statement = "SELECT DISTINCT city FROM airports ORDER BY city;";
            try {
                PreparedStatement query = conn.prepareStatement(statement);
                ResultSet res = query.executeQuery();
                while (res.next()) {
                    cityList.add(res.getString("city"));
                }
                query.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  cityList;
    }

    public static ObservableList<String> getCountryList() {
        if (countryList == null) {
            countryList = FXCollections.observableList(new LinkedList<String>());
            Connection conn = DataSource.getConnection();
            String statement = "SELECT DISTINCT country FROM airports ORDER BY country;";
            try {
                PreparedStatement query = conn.prepareStatement(statement);
                ResultSet res = query.executeQuery();
                while (res.next()) {
                    countryList.add(res.getString("country"));
                }
                query.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return  countryList;
    }

    public static ObservableList<String> getCityList(String country) {

        if (country == null) {
            return FXCollections.observableList(new LinkedList<>());
        }

        LinkedList<String> cityList = new LinkedList<>();

        Connection conn = DataSource.getConnection();
        String statement = "SELECT DISTINCT city FROM airports WHERE country = ? ORDER BY city DESC;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setString(1, country);
            ResultSet res = query.executeQuery();
            while (res.next()) {
                cityList.addFirst(res.getString("city"));
            }
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return FXCollections.observableList(cityList);
    }

    public static ObservableList<Airport> getAirportList(String city) {
        Connection conn = DataSource.getConnection();
        LinkedList<Airport> list = new LinkedList<>();

        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM airports WHERE city = ? ORDER BY name DESC;");
            query.setString(1, city);
            ResultSet res = query.executeQuery();
            while (res.next()) {
                Airport airport = new Airport();
                airport.setId(res.getInt("id"));
                airport.setName(res.getString("name"));
                airport.setCity(res.getString("city"));
                airport.setCountry(res.getString("country"));
                airport.setIATA(res.getString("IATA"));
                airport.setICAO(res.getString("ICAO"));


                list.addFirst(airport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableList(list);
    }

    @Override
    public int create(Airport airport) {
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
            return id.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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
