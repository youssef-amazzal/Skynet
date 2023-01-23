package data;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import models.Passenger;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PassengerDao implements Dao<Passenger> {
    public static final HashMap<Integer,Passenger> passengersMap = new HashMap<>();

    public void updatePassengersMap(int id) {
        read(id);
    }

    private byte[] imageToStream(Image logo) throws IOException {
        if (logo != null) {
            File file = new File("pic.png");
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
    public int create(Passenger passenger) {
        Connection conn = DataSource.getConnection();
        String statement = "INSERT INTO passengers (firstname, lastname, birthDate, gender, country, profilePicture) VALUES (?,?,?,?,?,?);";
        try {
            PreparedStatement query = conn.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            query.setString(1, passenger.getFirstname());
            query.setString(2, passenger.getLastname());
            query.setString(3, passenger.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            query.setString(4, passenger.getGender());
            query.setString(5, passenger.getCountry());
            query.setBytes(6, imageToStream(passenger.getProfilePictue()));

            query.executeUpdate();
            ResultSet id = query.getGeneratedKeys();
            if (id.next()) {
                passenger.setId(id.getInt(1));
                passengersMap.put(id.getInt(1), passenger);
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
    public Passenger read(int id) {

        Passenger passenger = passengersMap.get(id);
        if (passenger != null) {
            return passenger;
        }

        Connection conn = DataSource.getConnection();
        String statement = "SELECT * FROM passengers WHERE id = ?;";
        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(1, id);

            ResultSet res = query.executeQuery();

            if (res.next()) {
                passenger = new Passenger();
                passenger.setId(res.getInt("id"));
                passenger.setFirstname(res.getString("firstname"));
                passenger.setLastname(res.getString("lastname"));
                passenger.setBirthDate(LocalDate.parse(res.getString("birthDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                passenger.setGender(res.getString("gender"));
                passenger.setCountry(res.getString("country"));
                InputStream inputStream = res.getBinaryStream("profilePicture");
                if ((inputStream != null)) {
                    passenger.setProfilePictue(new Image(inputStream));
                } else {
                    passenger.setProfilePictue(null);
                }

                passengersMap.put(res.getInt("id"), passenger);
            }

            query.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return passenger;
    }


    @Override
    public List<Passenger> readAll() {
        Connection conn = DataSource.getConnection();
        List<Passenger> list = new ArrayList<>();

        try {
            PreparedStatement query = conn.prepareStatement("SELECT * FROM passengers;");
            ResultSet res = query.executeQuery();
            while (res.next()) {
                Passenger passenger = new Passenger();
                passenger.setId(res.getInt("id"));
                passenger.setFirstname(res.getString("firstname"));
                passenger.setLastname(res.getString("lastname"));
                passenger.setBirthDate(LocalDate.parse(res.getString("birthDate"), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                passenger.setGender(res.getString("gender"));
                passenger.setCountry(res.getString("country"));
                InputStream inputStream = res.getBinaryStream("profilePicture");
                if ((inputStream != null)) {
                    passenger.setProfilePictue(new Image(inputStream));
                } else {
                    passenger.setProfilePictue(null);
                }
                list.add(passenger);
                passengersMap.put(res.getInt("id"), passenger);
            }
            return list;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(int id, Passenger passenger) {
        Connection conn = DataSource.getConnection();
        Passenger original =  this.read(id);

        String statement = "UPDATE passengers SET firstname = ?, lastname = ?, birthDate = ?, gender = ?, country = ?, profilePicture = ? WHERE id = ? ;";

        try {
            PreparedStatement query = conn.prepareStatement(statement);
            query.setInt(7, id);

            if (passenger.getFirstname() != null) {
                query.setString(1, passenger.getFirstname());
                original.setFirstname(passenger.getFirstname());
            }
            else {
                query.setString(1, original.getFirstname());
            }

            if (passenger.getLastname() != null) {
                query.setString(2, passenger.getLastname());
                original.setLastname(passenger.getLastname());
            }
            else {
                query.setString(2, original.getLastname());
            }

            if (passenger.getBirthDate() != null) {
                query.setString(3, passenger.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                original.setBirthDate(passenger.getBirthDate());
            }
            else {
                query.setString(3, original.getBirthDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            }

            if (passenger.getGender() != null) {
                query.setString(4, passenger.getGender());
                original.setGender(passenger.getGender());
            }
            else {
                query.setString(4, original.getGender());
            }

            if (passenger.getCountry() != null) {
                query.setString(5, passenger.getCountry());
                original.setCountry(passenger.getCountry());
            }
            else {
                query.setString(5, original.getCountry());
            }

            if (passenger.getProfilePictue() != null) {
                query.setBytes(6, imageToStream(passenger.getProfilePictue()));
                original.setProfilePictue(passenger.getProfilePictue());
            }
            else {
                query.setBytes(6, imageToStream(original.getProfilePictue()));
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
            PreparedStatement query = conn.prepareStatement("DELETE FROM passengers WHERE id = ? ;");
            query.setInt(1, id);
            query.executeUpdate();
            query.close();

            passengersMap.remove(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
