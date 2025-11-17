package com.example.FlightBooking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI flightBookingAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080");
        devServer.setDescription("Development Server");

        Contact contact = new Contact();
        contact.setEmail("support@flightbooking.com");
        contact.setName("Flight Booking Support");
        contact.setUrl("https://flightbooking.com");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("Flight Booking System API")
                .version("1.0.0")
                .contact(contact)
                .description("REST API for managing flights, customers, and bookings. This API allows you to create, read, update, and delete flight bookings.")
                .termsOfService("https://flightbooking.com/terms")
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}