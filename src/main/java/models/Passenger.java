package models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.Objects;

public class Passenger {
    private int id;
    private final SimpleStringProperty firstname;
    private final SimpleStringProperty lastname;
    private LocalDate birthDate;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty country;
    private final ObjectProperty<Image> profilePictue;

    public Passenger() {
        this.firstname = new SimpleStringProperty();
        this.lastname = new SimpleStringProperty();
        this.gender = new SimpleStringProperty();
        this.country = new SimpleStringProperty();
        this.profilePictue = new SimpleObjectProperty<>();
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname.getValue();
    }
    public void setFirstname(String firstname) {
        this.firstname.setValue(firstname);
    }

    public SimpleStringProperty firstnameProperty() {
        return firstname;
    }

    public String getLastname() {
        return lastname.getValue();
    }
    public void setLastname(String lastname) {
        this.lastname.setValue(lastname);
    }
    public SimpleStringProperty lastnameProperty() {
        return lastname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender.get();
    }
    public SimpleStringProperty genderProperty() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public String getCountry() {
        return country.get();
    }
    public SimpleStringProperty countryProperty() {
        return country;
    }
    public void setCountry(String country) {
        this.country.set(country);
    }

    public Image getProfilePictue() {
        if (profilePictue.get() == null) {
            return new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/ProfilePicture.png")));
        }
        return profilePictue.get();
    }

    public ObjectProperty<Image> profilePictueProperty() {
        return profilePictue;
    }

    public void setProfilePictue(Image profilePictue) {
        this.profilePictue.set(profilePictue);
    }
}
