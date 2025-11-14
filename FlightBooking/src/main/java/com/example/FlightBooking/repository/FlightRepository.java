package com.example.FlightBooking.repository;

import com.example.FlightBooking.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByAirline(String airline);
}
