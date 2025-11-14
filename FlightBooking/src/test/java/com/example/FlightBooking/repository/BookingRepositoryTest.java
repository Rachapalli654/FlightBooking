package com.example.FlightBooking.repository;

import com.example.FlightBooking.entity.Booking;
import com.example.FlightBooking.entity.Customer;
import com.example.FlightBooking.entity.Flight;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Test
    void testFindByCustomerCustomerId() {
        Customer c = customerRepository.save(new Customer(null, "Mani", "test@gmail.com", null));
        Flight f = flightRepository.save(new Flight( "Indigo", 900));

        Booking booking = Booking.builder()
                .customer(c)
                .flight(f)
                .price(BigDecimal.valueOf(5000))
                .bookingDate(LocalDate.now())
                .build();

        bookingRepository.save(booking);

        List<Booking> result = bookingRepository.findByCustomerCustomerId(c.getCustomerId());

        assertThat(result).hasSize(1);
    }

    @Test
    void testFindByFlightFlightId() {
        Customer c = customerRepository.save(new Customer(null, "Arpitha", "arp@gmail.com", null));
        Flight f = flightRepository.save(new Flight("Indigo", 900));

        Booking booking = Booking.builder()
                .customer(c)
                .flight(f)
                .price(BigDecimal.valueOf(9000))
                .bookingDate(LocalDate.now())
                .build();

        bookingRepository.save(booking);

        List<Booking> result = bookingRepository.findByFlightFlightId(f.getFlightId());

        assertThat(result).hasSize(1);
    }
}
