package models;

import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Passenger {
    private int id;
    private final SimpleStringProperty firstname;
    private final SimpleStringProperty lastname;
    private LocalDate birthDate;

    public Passenger() {
        this.firstname = new SimpleStringProperty();
        this.lastname = new SimpleStringProperty();
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
}
