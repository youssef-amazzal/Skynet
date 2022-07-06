package data;

import models.Reserved_Seat;

import java.sql.*;
import java.util.*;

public class Reserved_SeatDao implements Dao<Reserved_Seat> {
    FlightDao flightDao = new FlightDao();
    SeatDao seatDao = new SeatDao();


    @Override
    public void create(Reserved_Seat reserved_seat) {
        Connection conn = DataSource.getConnection();
        String statement = "INSERT INTO reserved_seats ( id_seat,id_flight) VALUES (?,?);";
        try {
            PreparedStatement query = conn.prepareStatement(statement,Statement.RETURN_GENERATED_KEYS);
            query.setInt(1,reserved_seat.getSeat().getId());
            query.setInt(2, reserved_seat.getFlight().getId());

   

            query.executeUpdate();
            ResultSet id = query.getGeneratedKeys();
            if (id.next()) {
                reserved_seat.setId(id.getInt(1));
            }
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Reserved_Seat read(int id) {
        Connection conn = DataSource.getConnection();
        Reserved_seat reserved_seat = null;
        String statement = "SELECT * FROM reserved_seats WHERE id = ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(1, id);

            ResultSet res = query.executeQuery();

            if (res.next()) {
                reserved_seat = new Reserved_Seat();
                reserved_seat.setId(res.getInt("id"));
                reserved_seat.setSeat(seatDao.read(res.getInt("id_seat")));
                reserved_seat.setFlight(flightDao.read(res.getInt("id_flight")));


            }

            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reserved_seat;
    }


    @Override
    public List<Reserved_seat> readAll() {
        Connection conn = DataSource.getConnection();
        List<Reserved_Seat> list = new ArrayList<Reserved_Seat>();

        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM reserved_seats;");
            ResultSet res = query.executeQuery();
            while (res.next()) {
                Reserved_Seat reserved_seat = new Reserved_Seat();
                reserved_seat.setId(res.getInt("id"));
                reserved_seat.setSeat(seatDao.read(res.getInt("id_seat")));
                reserved_seat.setFlight(flightDao.read(res.getInt("id_flight")));

     

                list.add(reserved_seat);
            }
            return list;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
 @Override
    public void update(int id, Reserved_Seat reserved_seat) {
        Connection conn = DataSource.getConnection();
        Reserved_Seat original =  this.read(id);

        String statement = "UPDATE reserved_seats SET  id_seat = ? , id_flight = ? ;";

        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(2, id);

            if (reserved_seat.getSeat() != null) {
                query.setInt(1, reserved_seat.getSeat().getId());
            }
            else {
                query.setInt(1, original.getSeat().getId());
            }

            if (reserved_seat.getFlight() != null) {
                query.setInt(2, reserved_seat.getFlight().getId());
            }
            else {
                query.setInt(2, original.getFlight().getId());
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
            PreparedStatement query = conn.prepareStatement("DELETE FROM reserved_seats WHERE id = ? ;");
            query.setInt(1, id);
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
