package com.example.FlightBooking.controller;

import com.example.FlightBooking.dto.BookingRequest;
import com.example.FlightBooking.entity.Booking;
import com.example.FlightBooking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping(value = "/book", consumes = "application/json", produces = "application/json")
    public Booking bookFlight(@RequestBody BookingRequest request) {
        return bookingService.bookFlight(
                request.getFlightId(),
                request.getCustomerId(),
                request.getPrice()
        );
    }

    @GetMapping("/all")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/customer/{id}")
    public List<Booking> getBookingsByCustomer(@PathVariable("id") Long customerId) {
        return bookingService.getBookingsByCustomer(customerId);
    }

    @GetMapping("/flight/{id}")
    public List<Booking> getBookingsByFlight(@PathVariable("id") Long flightId) {
        return bookingService.getBookingsByFlight(flightId);
    }

    @DeleteMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable("id") Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return "Booking cancelled successfully!";
    }
}



   /*http://localhost:8080/api/customers/all
   http://localhost:8080/api/flights/all
   http://localhost:8080/api/bookings/all */
