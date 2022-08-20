package models;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Passenger {
    private int id;
    private final SimpleStringProperty firstname;
    private final SimpleStringProperty lastname;
    private LocalDate birthDate;
    private final SimpleStringProperty gender;
    private final SimpleStringProperty pays;

    public Passenger() {
        this.firstname = new SimpleStringProperty();
        this.lastname = new SimpleStringProperty();
        this.gender = new SimpleStringProperty();
        this.pays = new SimpleStringProperty();
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

    public String getPays() {
        return pays.get();
    }
    public SimpleStringProperty paysProperty() {
        return pays;
    }
    public void setPays(String pays) {
        this.pays.set(pays);
    }
}
