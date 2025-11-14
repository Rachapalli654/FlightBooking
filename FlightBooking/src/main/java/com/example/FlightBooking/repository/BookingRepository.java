package com.example.FlightBooking.repository;

import com.example.FlightBooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerCustomerId(Long customerId);
    List<Booking> findByFlightFlightId(Long flightId);
}
