package models;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Passenger {
    private int id;
    private final SimpleStringProperty firstname;
    private final SimpleStringProperty lastname;
    private LocalDate birthDate;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty country;

    public Passenger() {
        this.firstname = new SimpleStringProperty();
        this.lastname = new SimpleStringProperty();
        this.gender = new SimpleStringProperty();
        this.country = new SimpleStringProperty();
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
}
