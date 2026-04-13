package com.example.booking.Entity;

public class Seat {

    private int id;
    private boolean booked;
    private String user;

    public Seat(int id) {
        this.id = id;
        this.booked = false;
    }

    public int getId() { return id; }
    public boolean isBooked() { return booked; }
    public String getUser() { return user; }

    public void setBooked(boolean booked) { this.booked = booked; }
    public void setUser(String user) { this.user = user; }
}