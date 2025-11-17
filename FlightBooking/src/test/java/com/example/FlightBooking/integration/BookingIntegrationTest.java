package com.example.FlightBooking.integration;

import com.example.FlightBooking.dto.BookingRequest;
import com.example.FlightBooking.entity.Customer;
import com.example.FlightBooking.entity.Flight;
import com.example.FlightBooking.repository.BookingRepository;
import com.example.FlightBooking.repository.CustomerRepository;
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

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class BookingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer testCustomer;
    private Flight testFlight;

    @BeforeEach
    void setUp() {
        // Clean database before each test
        bookingRepository.deleteAll();
        customerRepository.deleteAll();
        flightRepository.deleteAll();

        // Create test customer
        testCustomer = Customer.builder()
                .name("Test Customer")
                .email("test@example.com")
                .build();
        testCustomer = customerRepository.save(testCustomer);

        // Create test flight
        testFlight = Flight.builder()
                .airline("Test Airlines")
                .totalSeats(100)
                .build();
        testFlight = flightRepository.save(testFlight);
    }

    @Test
    void testBookFlight_Success() throws Exception {
        // ARRANGE: Prepare booking request
        BookingRequest request = new BookingRequest();
        request.setFlightId(testFlight.getFlightId());
        request.setCustomerId(testCustomer.getCustomerId());
        request.setPrice(BigDecimal.valueOf(500.00));

        // ACT & ASSERT: Make HTTP POST request
        mockMvc.perform(post("/api/bookings/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").exists())
                .andExpect(jsonPath("$.price").value(500.00));
    }

    @Test
    void testGetAllBookings() throws Exception {
        // ARRANGE: Create a booking first
        BookingRequest request = new BookingRequest();
        request.setFlightId(testFlight.getFlightId());
        request.setCustomerId(testCustomer.getCustomerId());
        request.setPrice(BigDecimal.valueOf(500.00));

        // Book a flight
        mockMvc.perform(post("/api/bookings/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // ACT & ASSERT: Get all bookings
        mockMvc.perform(get("/api/bookings/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testGetBookingsByCustomer() throws Exception {
        // ARRANGE: Book a flight
        BookingRequest request = new BookingRequest();
        request.setFlightId(testFlight.getFlightId());
        request.setCustomerId(testCustomer.getCustomerId());
        request.setPrice(BigDecimal.valueOf(500.00));

        mockMvc.perform(post("/api/bookings/book")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // ACT & ASSERT: Get bookings by customer
        mockMvc.perform(get("/api/bookings/customer/" + testCustomer.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testCancelBooking() throws Exception {
        // ARRANGE: Book a flight first
        BookingRequest request = new BookingRequest();
        request.setFlightId(testFlight.getFlightId());
        request.setCustomerId(testCustomer.getCustomerId());
        request.setPrice(BigDecimal.valueOf(500.00));

        String response = mockMvc.perform(post("/api/bookings/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn()
                .getResponse()
                .getContentAsString();

// Parse JSON
        Long bookingId = objectMapper.readTree(response).get("bookingId").asLong();


        // ACT & ASSERT: Cancel the booking
        mockMvc.perform(delete("/api/bookings/cancel/" + bookingId))
                .andExpect(status().isOk())
                .andExpect(content().string("Booking cancelled successfully!"));
    }
}