package com.example.FlightBooking.controller;

import com.example.FlightBooking.dto.BookingRequest;
import com.example.FlightBooking.entity.Booking;
import com.example.FlightBooking.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookingController).build();
    }

    @Test
    void testBookFlight() throws Exception {
        BookingRequest req = new BookingRequest();
        req.setCustomerId(1L);
        req.setFlightId(1L);
        req.setPrice(BigDecimal.valueOf(5000));

        Mockito.when(bookingService.bookFlight(1L, 1L, BigDecimal.valueOf(5000)))
                .thenReturn(new Booking());

        mockMvc.perform(post("/api/bookings/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"flightId\":1,\"customerId\":1,\"price\":5000}"))
                .andExpect(status().isOk());
    }
}
