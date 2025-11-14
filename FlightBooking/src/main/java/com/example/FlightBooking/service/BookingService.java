package com.example.FlightBooking.service;

import com.example.FlightBooking.entity.Booking;
import com.example.FlightBooking.entity.Flight;
import com.example.FlightBooking.entity.Customer;
import com.example.FlightBooking.repository.BookingRepository;
import com.example.FlightBooking.repository.CustomerRepository;
import com.example.FlightBooking.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final CustomerRepository customerRepository;

    public Booking bookFlight(Long flightId, Long customerId, BigDecimal price) {
        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight not found"));

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Booking booking = Booking.builder()
                .flight(flight)
                .customer(customer)
                .price(price)
                .bookingDate(LocalDate.now())
                .build();

        return bookingRepository.save(booking);
    }


    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public List<Booking> getBookingsByCustomer(Long customerId) {
        return bookingRepository.findByCustomerCustomerId(customerId);
    }

    public List<Booking> getBookingsByFlight(Long flightId) {
        return bookingRepository.findByFlightFlightId(flightId);
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        bookingRepository.delete(booking);
    }
}
