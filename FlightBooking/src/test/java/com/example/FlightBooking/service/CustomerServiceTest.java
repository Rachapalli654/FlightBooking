package com.example.FlightBooking.service;

import com.example.FlightBooking.entity.Customer;
import com.example.FlightBooking.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    CustomerServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterCustomer() {
        Customer c = new Customer(1L, "Mani", "mani@gmail.com", null);

        when(customerRepository.save(c)).thenReturn(c);

        Customer saved = customerService.registerCustomer(c);

        assertThat(saved.getName()).isEqualTo("Mani");
    }
}
