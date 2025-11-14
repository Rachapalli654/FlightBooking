package com.example.FlightBooking.service;

import com.example.FlightBooking.entity.Booking;
import com.example.FlightBooking.entity.Customer;
import com.example.FlightBooking.entity.Flight;
import com.example.FlightBooking.repository.BookingRepository;
import com.example.FlightBooking.repository.CustomerRepository;
import com.example.FlightBooking.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private BookingService bookingService;

    private Customer customer;
    private Flight flight;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customer = new Customer(1L, "Mani", "mani@gmail.com", null);
        flight = new Flight(1L, "Indigo", 900);
    }

    @Test
    void testBookFlight() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));
        when(bookingRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Booking result = bookingService.bookFlight(1L, 1L, BigDecimal.valueOf(5000));

        assertThat(result.getCustomer()).isEqualTo(customer);
        assertThat(result.getFlight()).isEqualTo(flight);
    }
}
