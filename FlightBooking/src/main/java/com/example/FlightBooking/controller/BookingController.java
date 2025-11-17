package com.example.FlightBooking.controller;

import com.example.FlightBooking.dto.BookingRequest;
import com.example.FlightBooking.entity.Booking;
import com.example.FlightBooking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
@Tag(name = "Booking Management", description = "APIs for managing flight bookings")
public class BookingController {

    private final BookingService bookingService;

    @PostMapping(value = "/book", consumes = "application/json", produces = "application/json")
    @Operation(
            summary = "Book a flight",
            description = "Create a new flight booking for a customer"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking created successfully"),
            @ApiResponse(responseCode = "404", description = "Flight or Customer not found")
    })
    public Booking bookFlight(@RequestBody BookingRequest request) {
        return bookingService.bookFlight(
                request.getFlightId(),
                request.getCustomerId(),
                request.getPrice()
        );
    }

    @GetMapping("/all")
    @Operation(summary = "Get all bookings", description = "Retrieve all flight bookings")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/customer/{id}")
    @Operation(summary = "Get bookings by customer")
    public List<Booking> getBookingsByCustomer(
            @Parameter(description = "Customer ID") @PathVariable("id") Long customerId) {
        return bookingService.getBookingsByCustomer(customerId);
    }

    @GetMapping("/flight/{id}")
    @Operation(summary = "Get bookings by flight")
    public List<Booking> getBookingsByFlight(
            @Parameter(description = "Flight ID") @PathVariable("id") Long flightId) {
        return bookingService.getBookingsByFlight(flightId);
    }

    @DeleteMapping("/cancel/{id}")
    @Operation(summary = "Cancel a booking")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Booking cancelled"),
            @ApiResponse(responseCode = "404", description = "Booking not found")
    })
    public String cancelBooking(
            @Parameter(description = "Booking ID") @PathVariable("id") Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return "Booking cancelled successfully!";
    }
}