package models;

import java.sql.*;

public class Flight {
    private int id;
    private Double dep_datetime;
    private Double arr_datetime;
    private Double first_price;
    private Double business_price;
    private Double economy_price;
    private Double luggage_price;
    private Double weight_price;
    private Airport dep_airport;
    private Airport arr_airport;
    private Airline id_airline;


    public Flight() {
        this.dep_datetime = null;
        this.arr_datetime= null;
        this.first_price = null;
        this.business_price = null;
        this.economy_price = null;
        this.luggage_price = null;
        this.weight_price = null;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Double getDep_Datetime() {
        return dep_datetime;
    }
    public void setDep_Datetime(Double dep_datetime) {
        this.dep_datetime = dep_datetime;
    }

    public Double getArr_Datetime() {
        return arr_datetime;
    }
    public void setArr_Datetime(Double arr_datetime) {
        this.arr_datetime = arr_datetime;
    }

    public Double getFirst_Price() {
        return first_price;
    }
    public void setFirst_Price(Double first_price) {
        this.first_price = first_price;
    }

    public Double getEconomy_Price() {
        return economy_price;
    }
    public void setEconomy_Price(Double economy_price) {
        this.economy_price = economy_price;
    }

    public Double getLuggage_Price() {
        return luggage_price;
    }
    public void setLuggage_Price(Double luggage_price) {
        this.luggage_price = luggage_price;
    }

    public Double getWeight_Price() {
        return weight_price;
    }
    public void setWeight_Price(Double weight_price) {
        this.weight_price = weight_price;
    }

    public Airline getid_airline() {
        return id_airline;
    }
    public void setid_airline(Airline id_airline) {
        this.id_airline = id_airline;
    }

    public Airport getdep_airport() {
        return dep_airport;
    }
    public void setdep_airport(Airport dep_airport) {
        this.dep_airport = dep_airport;
    }

    public Airport getarr_airport() {
        return arr_airport;
    }
    public void setarr_airprt(Airport arr_airport) {
        this.arr_airport = arr_airport;
    }

}
