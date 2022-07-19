package data;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import models.Airline;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AirlineDao implements Dao<Airline> {
    public static final HashMap<Integer, Airline> airlinesMap = new HashMap<>();

    public void updateAirlinesMap(int id) {
        read(id);
    }

    private FileInputStream imageToStream(Image logo) throws IOException {
        File file = new File("logo.png");
        ImageIO.write(SwingFXUtils.fromFXImage(logo, null), "png", file);
        return new FileInputStream(file);
    }

    @Override
    public void create(Airline airline) {
        Connection conn = DataSource.getConnection();
        String statement = "INSERT INTO airlines (name, logo) VALUES (?,?);";
        try {
            PreparedStatement query = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            query.setString(1, airline.getName());
            query.setBinaryStream(2, imageToStream(airline.getLogo()));


            query.executeUpdate();
            ResultSet id = query.getGeneratedKeys();
            if (id.next()) {
                airline.setId(id.getInt(1));
                airlinesMap.put(id.getInt(1), airline);
            }
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Airline read(int id) {
        Connection conn = DataSource.getConnection();
        Airline airline = null;
        String statement = "SELECT * FROM airlines WHERE id = ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(1, id);

            ResultSet res = query.executeQuery();

            if (res.next()) {
                airline = new Airline();
                airline.setId(res.getInt("id"));
                airline.setName(res.getString("name"));
                airline.setLogo(new Image(res.getBinaryStream("logo")));
                airlinesMap.put(res.getInt("id"), airline);
            }

            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return airline;
    }


    @Override
    public List<Airline> readAll() {
        Connection conn = DataSource.getConnection();
        List<Airline> list = new ArrayList<Airline>();

        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM airlines;");
            ResultSet res = query.executeQuery();
            while (res.next()) {
                Airline airline = new Airline();
                airline.setId(res.getInt("id"));
                airline.setName(res.getString("name"));
                airline.setLogo(new Image(res.getBinaryStream("logo")));

                list.add(airline);
                airlinesMap.put(res.getInt("id"), airline);
            }
            return list;
        } catch (SQLException e) {

            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(int id, Airline airline) {
        Connection conn = DataSource.getConnection();
        Airline original =  this.read(id);

        String statement = "UPDATE airlines SET name = ?, logo = ? WHERE id = ? ;";

        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(3, id);

            if (airline.getName() != null) {
                query.setString(1, airline.getName());
            }
            else {
                query.setString(1, original.getName());
            }

            if (airline.getLogo() != null) {
                query.setBinaryStream(2, imageToStream(airline.getLogo()));
            }
            else {
                query.setBinaryStream(2, imageToStream(original.getLogo()));
            }
            query.executeUpdate();
            query.close();
            read(id);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        Connection conn = DataSource.getConnection();
        try {
            PreparedStatement query = conn.prepareStatement("DELETE FROM airlines WHERE id = ? ;");
            query.setInt(1, id);
            query.executeUpdate();
            query.close();

            airlinesMap.remove(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
