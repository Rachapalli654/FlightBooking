package com.example.FlightBooking.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BookingRequest {
    private Long flightId;
    private Long customerId;
    private BigDecimal price;
    private LocalDate bookingDate;
}
