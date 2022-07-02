package data;

import java.sql.*;
import java.util.*;

import models.Seat;

public class SeatDao implements Dao<Seat> {

    @Override
    public void create(Seat seat) {
        Connection conn = DataSource.getConnection();
        String statement = "INSERT INTO seats (column, row, type) VALUES (?,?,?);";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setString(1, seat.getColumn());
            query.setInt(2, seat.getRow());
            query.setString(3, seat.getType());
            

            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Seat read(int id) {
        Connection conn = DataSource.getConnection();
        Seat seat = null;
        String statement = "SELECT * FROM seats WHERE id = ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(1, id);

            ResultSet res = query.executeQuery();

            if (res.next()) {
                seat = new Seat();
                seat.setId(res.getInt("id"));
                seat.setColumn(res.getString("column"));
                seat.setRow(res.getInt("row"));
                seat.setType(res.getString("type"));

            }

            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return seat;
    }


    @Override
    public List<Seat> readAll() {
        Connection conn = DataSource.getConnection();
        List<Seat> list = new ArrayList<Seat>();

        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM seats;");
            ResultSet res = query.executeQuery();
            while (res.next()) {
                Seat seat = new Seat();
                seat.setId(res.getInt("id"));
                seat.setColumn(res.getString("column"));
                seat.setRow(res.getInt("row"));
                seat.setType(res.getString("type"));

                list.add(seat);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(int id, Seat seat) {
        Connection conn = DataSource.getConnection();
        Seat original =  this.read(id);

        String statement = "UPDATE seat SET column = ?, row = ?, type = ? WHERE id = ? ;";

        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(3, id);

            if (seat.getFirstname() != null) {
                query.setString(1, seat.getColumn());
            }
            else {
                query.setString(1, original.getColumn());
            }

            if (seat.getRow() != null) {
                query.setString(2, seat.getRow());
            }
            else {
                query.setString(2, original.getRow());
            }

            if (seat.getType() != null) {
                query.setString(2, seat.getType());
            }
            else {
                query.setString(2, original.getType());
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
            PreparedStatement query = conn.prepareStatement("DELETE FROM seats WHERE id = ? ;");
            query.setInt(1, id);
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
