package com.example.FlightBooking.integration;

import com.example.FlightBooking.entity.Flight;
import com.example.FlightBooking.repository.FlightRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class FlightIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        flightRepository.deleteAll();
    }

    @Test
    void testAddFlight_Success() throws Exception {
        Flight flight = Flight.builder()
                .airline("Emirates")
                .totalSeats(200)
                .build();

        mockMvc.perform(post("/api/flights/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(flight)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airline").value("Emirates"))
                .andExpect(jsonPath("$.totalSeats").value(200));
    }

    @Test
    void testGetAllFlights() throws Exception {
        // Create 2 flights
        flightRepository.save(Flight.builder().airline("Emirates").totalSeats(200).build());
        flightRepository.save(Flight.builder().airline("Qatar").totalSeats(150).build());

        mockMvc.perform(get("/api/flights/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetFlightById() throws Exception {
        Flight flight = flightRepository.save(
                Flight.builder().airline("Emirates").totalSeats(200).build()
        );

        mockMvc.perform(get("/api/flights/" + flight.getFlightId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.airline").value("Emirates"));
    }

    @Test
    void testDeleteFlight() throws Exception {
        Flight flight = flightRepository.save(
                Flight.builder().airline("Emirates").totalSeats(200).build()
        );

        mockMvc.perform(delete("/api/flights/delete/" + flight.getFlightId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Flight deleted successfully!"));
    }
}