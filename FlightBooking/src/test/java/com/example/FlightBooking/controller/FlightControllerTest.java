package com.example.FlightBooking.controller;

import com.example.FlightBooking.entity.Flight;
import com.example.FlightBooking.service.FlightService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FlightController.class)
class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService service;

    @Test
    void testGetFlightById() throws Exception {
        when(service.getFlightById(1L)).thenReturn(java.util.Optional.of(new Flight()));

        mockMvc.perform(get("/api/flights/1"))
                .andExpect(status().isOk());
    }
}
