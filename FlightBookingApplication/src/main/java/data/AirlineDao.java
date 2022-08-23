package data;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import models.Airline;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AirlineDao implements Dao<Airline> {
    public static final HashMap<Integer, Airline> airlinesMap = new HashMap<>();

    public void updateAirlinesMap(int id) {
        read(id);
    }

    private byte[] imageToStream(Image logo) throws IOException {
        if (logo != null) {
            File file = new File("logo.png");
            ImageIO.write(SwingFXUtils.fromFXImage(logo, null), "png", file);
            FileInputStream fileInputStream =  new FileInputStream(file);
            byte[] byteArray = fileInputStream.readAllBytes() ;
            fileInputStream.close();
            file.delete();
            return byteArray;
        }
        return null;
    }

    @Override
    public int create(Airline airline) {
        Connection conn = DataSource.getConnection();
        String statement = "INSERT INTO airlines (name, logo, IATA) VALUES (?,?,?);";
        try {
            PreparedStatement query = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            query.setString(1, airline.getName());
            query.setBytes(2, imageToStream(airline.getLogo()));
            query.setString(3, airline.getIATA());

            query.executeUpdate();
            ResultSet id = query.getGeneratedKeys();
            if (id.next()) {
                airline.setId(id.getInt(1));
                airlinesMap.put(id.getInt(1), airline);
            }
            query.close();
            return id.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return 0;
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
                airline.setIATA(res.getString("IATA"));
                InputStream inputStream = res.getBinaryStream("logo");
                if ((inputStream != null)) {
                    airline.setLogo(new Image(inputStream));
                } else {
                    airline.setLogo(null);
                }
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
        LinkedList<Airline> list = new LinkedList<>();

        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM airlines;");
            ResultSet res = query.executeQuery();
            while (res.next()) {
                Airline airline = new Airline();
                airline.setId(res.getInt("id"));
                airline.setName(res.getString("name"));
                airline.setIATA(res.getString("IATA"));
                InputStream inputStream = res.getBinaryStream("logo");
                if ((inputStream != null)) {
                    airline.setLogo(new Image(inputStream));
                } else {
                    airline.setLogo(null);
                }

                list.addFirst(airline);
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

        String statement = "UPDATE airlines SET name = ?, logo = ?, IATA = ? WHERE id = ? ;";

        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(4, id);

            if (airline.getName() != null) {
                query.setString(1, airline.getName());
            }
            else {
                query.setString(1, original.getName());
            }

            if (airline.getLogo() != null) {
                query.setBytes(2, imageToStream(airline.getLogo()));
            }
            else {
                query.setBytes(2, imageToStream(original.getLogo()));
            }

            if (airline.getIATA() != null) {
                query.setString(3, airline.getIATA());
            }
            else {
                query.setString(3, original.getIATA());
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
