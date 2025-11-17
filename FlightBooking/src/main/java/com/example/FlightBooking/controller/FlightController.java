package com.example.FlightBooking.controller;

import com.example.FlightBooking.entity.Flight;
import com.example.FlightBooking.service.FlightService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
@Tag(name = "Flight Management", description = "APIs for managing flights")
public class FlightController {

    private final FlightService flightService;

    @PostMapping("/add")
    @Operation(summary = "Add a new flight", description = "Create a new flight in the system")
    public Flight addFlight(@RequestBody Flight flight) {
        return flightService.addFlight(flight);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all flights", description = "Retrieve all available flights")
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get flight by ID")
    public Optional<Flight> getFlightById(
            @Parameter(description = "Flight ID") @PathVariable Long id) {
        return flightService.getFlightById(id);
    }

    @GetMapping("/airline")
    @Operation(summary = "Get flights by airline")
    public List<Flight> getFlightsByAirline(
            @Parameter(description = "Airline name") @RequestParam String airline) {
        return flightService.getFlightsByAirline(airline);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a flight")
    public String deleteFlight(@Parameter(description = "Flight ID") @PathVariable Long id) {
        flightService.deleteFlight(id);
        return "Flight deleted successfully!";
    }
}