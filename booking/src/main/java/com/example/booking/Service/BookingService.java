package com.example.booking.Service;

import com.example.booking.Entity.Seat;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.util.*;

@Service
public class BookingService {

    private List<Seat> seats = new ArrayList<>();
    private int totalSold = 0;

    @PostConstruct
    public void init() {
        initialize();
    }

    public synchronized void initialize() {
        seats.clear();
        totalSold = 0;

        for (int i = 1; i <= 100; i++) {
            seats.add(new Seat(i));
        }
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public synchronized int bookSeats(List<Integer> seatIds, String user) {

        for (int id : seatIds) {
            if (seats.get(id - 1).isBooked()) {
                throw new RuntimeException("Seat already booked: " + id);
            }
        }

        int totalPrice = 0;

        for (int id : seatIds) {
            totalSold++;

            if (totalSold <= 50) totalPrice += 50;
            else if (totalSold <= 80) totalPrice += 75;
            else totalPrice += 100;

            Seat seat = seats.get(id - 1);
            seat.setBooked(true);
            seat.setUser(user);
        }

        return totalPrice;
    }
}