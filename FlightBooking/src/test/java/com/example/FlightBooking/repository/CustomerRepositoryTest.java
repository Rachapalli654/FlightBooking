package com.example.FlightBooking.repository;

import com.example.FlightBooking.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testSaveCustomer() {
        Customer c = new Customer(null, "Manikanta", "mani@gmail.com", null);
        Customer saved = customerRepository.save(c);

        assertThat(saved.getCustomerId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Manikanta");
    }
}
