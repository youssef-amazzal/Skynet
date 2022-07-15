package models;

public class Reservation {
    private int id;
    private Flight flight;
    private Account account;
    private Seat seat;
    private int nbrLuggages;
    private double weight;

    public Reservation(Flight flight, Account account, Seat seat, int nbrLuggages, double weight) {
        this.flight = flight;
        this.seat = seat;
        this.account = account;
        this.nbrLuggages = nbrLuggages;
        this.weight = weight;
    }

    public Reservation() {
        this.weight = -1;
        this.nbrLuggages = -1;
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public int getNbrLuggages() {
        return nbrLuggages;
    }

    public void setNbrLuggages(int nbrLuggages) {
        this.nbrLuggages = nbrLuggages;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
