package com.example.booking.Controller;

import com.example.booking.Entity.Seat;
import com.example.booking.Service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    // 1. Initialize
    @PostMapping("/initialize")
    public String initialize() {
        service.initialize();
        return "Event initialized with 100 seats";
    }

    // 2. Get seats
    @GetMapping("/seats")
    public List<Seat> getSeats() {
        return service.getSeats();
    }

    // 3. Book seats
    @PostMapping("/book")
    public Map<String, Object> bookSeats(@RequestBody Map<String, Object> request) {

        List<Integer> seatIds = (List<Integer>) request.get("seatIds");
        String user = (String) request.get("user");

        int totalPrice = service.bookSeats(seatIds, user);

        return Map.of("totalPrice", totalPrice);
    }
}