package nl.hkstwk.spring6reactive.controllers;

import nl.hkstwk.spring6reactive.domain.Customer;
import nl.hkstwk.spring6reactive.domain.CustomerDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class CustomerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    void listCustomers() {
        webTestClient.get().uri(CustomerController.CUSTOMER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBodyList(CustomerDTO.class).hasSize(2);
    }

    @Test
    @Order(2)
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
                .body(Mono.just(getTestCustomer()), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/customer/3");
    }

    @Test
    @Order(3)
    void updateCustomer() {
        Customer testCustomer = getTestCustomer();
        testCustomer.setCustomerName("Cafe het Pleintje updated");
        webTestClient.put().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(testCustomer), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deleteCustomer() {
        webTestClient.delete().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    private static Customer getTestCustomer(){
        return Customer.builder()
                .customerName("Cafe het Pleintje")
                .build();
    }
}