package com.example.FlightBooking.controller;

import com.example.FlightBooking.entity.Customer;
import com.example.FlightBooking.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService service;

    @Test
    void testGetCustomerById() throws Exception {
        when(service.getCustomerById(1L)).thenReturn(java.util.Optional.of(new Customer()));

        mockMvc.perform(get("/api/customers/1"))
                .andExpect(status().isOk());
    }
}
