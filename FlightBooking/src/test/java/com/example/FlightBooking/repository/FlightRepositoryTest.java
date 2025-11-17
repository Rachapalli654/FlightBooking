package com.example.FlightBooking.repository;

import com.example.FlightBooking.entity.Flight;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FlightRepositoryTest {

    @Autowired
    private FlightRepository flightRepository;

    @Test
    void testFindByAirline() {
        Flight f = new Flight(1L, "Indigo", 900);
        flightRepository.save(f);

        List<Flight> flights = flightRepository.findByAirline("Indigo");

        assertThat(flights).hasSize(1);
    }
}