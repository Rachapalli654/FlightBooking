package com.example.FlightBooking.controller;

import com.example.FlightBooking.entity.Customer;
import com.example.FlightBooking.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin("*")
@Tag(name = "Customer Management", description = "APIs for managing customers")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    @Operation(summary = "Register a new customer", description = "Create a new customer account")
    public Customer registerCustomer(@RequestBody Customer customer) {
        return customerService.registerCustomer(customer);
    }

    @GetMapping("/all")
    @Operation(summary = "Get all customers", description = "Retrieve all registered customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID")
    public Optional<Customer> getCustomerById(
            @Parameter(description = "Customer ID") @PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete a customer")
    public String deleteCustomer(@Parameter(description = "Customer ID") @PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "Customer deleted successfully!";
    }
}