package data;

import javafx.scene.image.Image;
import models.Airline;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AirlineDao implements Dao<Airline> {

    private Image retrieveImage(String imageName, InputStream inputStream) throws IOException {
        Image image = null;
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        if(inputReader.ready())
        {
            File tempFile = new File("/images/"+imageName+".png");

            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            byte[] buffer = new byte[1024];
            while(inputStream.read(buffer) > 0){
                fileOutputStream.write(buffer);
            }
            image = new Image(tempFile.toURI().toURL().toString());
        }
        return image;
    }

    /*private byte[] imageToByteArray(Image image) {
    }*/

    @Override
    public void create(Airline airline) {
        Connection conn = DataSource.getConnection();
        String statement = "INSERT INTO airlines (name, logo) VALUES (?,?);";
        try {
            PreparedStatement query = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            query.setString(1, airline.getName());
            query.setString(2, airline.getLogo());


            query.executeUpdate();
            ResultSet id = query.getGeneratedKeys();
            if (id.next()) {
                airline.setId(id.getInt(1));
            }
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
                airline.setLogo(res.getString("logo"));

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
                airline.setLogo(res.getString("logo"));


                list.add(airline);
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
                query.setString(2, airline.getLogo());
            }
            else {
                query.setString(2, original.getLogo());
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
            PreparedStatement query = conn.prepareStatement("DELETE FROM airlines WHERE id = ? ;");
            query.setInt(1, id);
            query.executeUpdate();
            query.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
