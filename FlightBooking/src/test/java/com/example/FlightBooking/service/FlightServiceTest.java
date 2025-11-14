package com.example.FlightBooking.service;

import com.example.FlightBooking.entity.Flight;
import com.example.FlightBooking.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    FlightServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddFlight() {
        Flight f = new Flight(1L, "Indigo", 900);

        when(flightRepository.save(f)).thenReturn(f);

        Flight result = flightService.addFlight(f);

        assertThat(result.getAirline()).isEqualTo("Indigo");
    }
}
