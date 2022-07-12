package data;

import models.Flight;
import models.ReservedSeat;
import models.Seat;

import java.sql.*;
import java.util.*;

public class ReservedSeatDao implements Dao<ReservedSeat> {
    FlightDao flightDao = new FlightDao();
    SeatDao seatDao = new SeatDao();


    @Override
    public void create(ReservedSeat reservedSeat) {
        Connection conn = DataSource.getConnection();
        String statement = "INSERT INTO reserved_seats (id_seat,id_flight) VALUES (?,?);";
        try {
            PreparedStatement query = conn.prepareStatement(statement,Statement.RETURN_GENERATED_KEYS);
            query.setInt(1,reservedSeat.getSeat().getPrimaryKey());
            query.setInt(2, reservedSeat.getFlight().getId());

   

            query.executeUpdate();
            ResultSet id = query.getGeneratedKeys();
            if (id.next()) {
                reservedSeat.setId(id.getInt(1));
            }
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ReservedSeat read(int id) {
        Connection conn = DataSource.getConnection();
        ReservedSeat reservedSeat = null;
        String statement = "SELECT * FROM reserved_seats WHERE id = ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(1, id);

            ResultSet res = query.executeQuery();

            if (res.next()) {
                reservedSeat = new ReservedSeat();
                reservedSeat.setId(res.getInt("id"));
                reservedSeat.setSeat(seatDao.read(res.getInt("id_seat")));
                reservedSeat.setFlight(flightDao.read(res.getInt("id_flight")));


            }

            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservedSeat;
    }

    @Override
    public List<ReservedSeat> readAll() {
        Connection conn = DataSource.getConnection();
        List<ReservedSeat> list = new ArrayList<ReservedSeat>();

        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM reserved_seats;");
            ResultSet res = query.executeQuery();
            while (res.next()) {
                ReservedSeat reservedSeat = new ReservedSeat();
                reservedSeat.setId(res.getInt("id"));
                reservedSeat.setSeat(seatDao.read(res.getInt("id_seat")));
                reservedSeat.setFlight(flightDao.read(res.getInt("id_flight")));

     

                list.add(reservedSeat);
            }
            return list;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
 @Override
    public void update(int id, ReservedSeat reservedSeat) {
        Connection conn = DataSource.getConnection();
        ReservedSeat original =  this.read(id);

        String statement = "UPDATE reserved_seats SET  id_seat = ? , id_flight = ? WHERE id = ?;";

        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(3, id);

            if (reservedSeat.getSeat() != null) {
                query.setInt(1, reservedSeat.getSeat().getPrimaryKey());
            }
            else {
                query.setInt(1, original.getSeat().getPrimaryKey());
            }

            if (reservedSeat.getFlight() != null) {
                query.setInt(2, reservedSeat.getFlight().getId());
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
