package nl.hkstwk.spring6reactive.controllers;

import nl.hkstwk.spring6reactive.domain.Beer;
import nl.hkstwk.spring6reactive.domain.Customer;
import nl.hkstwk.spring6reactive.domain.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureWebTestClient
class CustomerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void listCustomers() {
        webTestClient.get().uri(CustomerController.CUSTOMER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBodyList(CustomerDTO.class).hasSize(2);
    }

    @Test
    void getBeerById() {
        webTestClient.get().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody(CustomerDTO.class);
    }

    @Test
    void createCustomer() {
        webTestClient.post().uri(CustomerController.CUSTOMER_PATH)
                .body(getTestCustomer(), Customer.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().valueEquals("asasa");
    }

    @Test
    void updateCustomer() {
    }

    @Test
    void deleteCustomer() {
    }

    private static Customer getTestCustomer(){
        return Customer.builder()
                .customerName("Cafe het Pleintje")
                .build();
    }
}