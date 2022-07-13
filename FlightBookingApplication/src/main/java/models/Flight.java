package models;

import java.time.LocalDateTime;

public class Flight {
    private int id;
    private LocalDateTime depDatetime;
    private LocalDateTime arrDatetime;
    private double firstPrice;
    private double businessPrice;
    private double economyPrice;
    private double luggagePrice;
    private double weightPrice;
    private Airport depAirport;
    private Airport arrAirport;
    private Airline airline;

    public Flight() {
        this.firstPrice = -1;
        this.businessPrice = -1;
        this.economyPrice = -1;
        this.luggagePrice = -1;
        this.weightPrice = -1;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDepDatetime() {
        return depDatetime;
    }
    public void setDepDatetime(LocalDateTime dep_datetime) {
        this.depDatetime = dep_datetime;
    }

    public LocalDateTime getArrDatetime() {
        return arrDatetime;
    }
    public void setArrDatetime(LocalDateTime arr_datetime) {
        this.arrDatetime = arr_datetime;
    }

    public double getFirstPrice() {
        return firstPrice;
    }
    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline id_airline) {
        this.airline = id_airline;
    }
    public Airport getDepAirport() {
        return depAirport;
    }

    public void setDepAirport(Airport dep_airport) {
        this.depAirport = dep_airport;
    }
    public Airport getArrAirport() {
        return arrAirport;
    }

    public void setArrAirport(Airport arr_airport) {
        this.arrAirport = arr_airport;
    }
    public String getFirstPriceFormatted() {
        return String.format("%.02f$",firstPrice);
    }
    public void setFirstPrice(double first_price) {
        this.firstPrice = first_price;
    }

    public double getBusinessPrice() {
        return businessPrice;
    }
    public String getBusinessPriceFormatted() {
        return String.format("%.02f$",businessPrice);
    }
    public void setBusinessPrice(double businessPrice) {
        this.businessPrice = businessPrice;
    }

    public double getEconomyPrice() {
        return economyPrice;
    }
    public String getEconomyPriceFormatted() {
        return String.format("%.02f$",economyPrice);
    }
    public void setEconomyPrice(double economy_price) {
        this.economyPrice = economy_price;
    }

    public double getLuggagePrice() {
        return luggagePrice;
    }
    public void setLuggagePrice(double luggage_price) {
        this.luggagePrice = luggage_price;
    }

    public double getWeightPrice() {
        return weightPrice;
    }
    public void setWeightPrice(double weight_price) {
        this.weightPrice = weight_price;
    }
}
