package com.example.FlightBooking.integration;

import com.example.FlightBooking.entity.Customer;
import com.example.FlightBooking.repository.CustomerRepository;
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
class CustomerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    void testRegisterCustomer_Success() throws Exception {
        Customer customer = Customer.builder()
                .name("John Doe")
                .email("john@example.com")
                .build();

        mockMvc.perform(post("/api/customers/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void testGetAllCustomers() throws Exception {
        customerRepository.save(Customer.builder().name("Alice").email("alice@test.com").build());
        customerRepository.save(Customer.builder().name("Bob").email("bob@test.com").build());

        mockMvc.perform(get("/api/customers/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetCustomerById() throws Exception {
        Customer customer = customerRepository.save(
                Customer.builder().name("Jane Doe").email("jane@test.com").build()
        );

        mockMvc.perform(get("/api/customers/" + customer.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Customer customer = customerRepository.save(
                Customer.builder().name("Test User").email("test@test.com").build()
        );

        mockMvc.perform(delete("/api/customers/delete/" + customer.getCustomerId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer deleted successfully!"));
    }
}
