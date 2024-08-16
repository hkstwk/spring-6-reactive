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

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class CustomerControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    void listCustomers() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get().uri(CustomerController.CUSTOMER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBodyList(CustomerDTO.class).hasSize(2);
    }

    @Test
    @Order(2)
    void getBeerById() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-Type", "application/json")
                .expectBody(CustomerDTO.class);
    }

    @Test
    void getBeerByIdNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get().uri(CustomerController.CUSTOMER_PATH_ID, 999)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void createCustomerInvalidData() {
        Customer testCustomer = getTestCustomer();
        testCustomer.setCustomerName("");

        webTestClient
                .mutateWith(mockOAuth2Login())
                .post().uri(CustomerController.CUSTOMER_PATH)
                .body(Mono.just(testCustomer), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void createCustomer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .post().uri(CustomerController.CUSTOMER_PATH)
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

        webTestClient
                .mutateWith(mockOAuth2Login())
                .put().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(testCustomer), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(4)
    void updateCustomerInvalidData() {
        Customer testCustomer = getTestCustomer();
        testCustomer.setCustomerName("");

        webTestClient
                .mutateWith(mockOAuth2Login())
                .put().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .body(Mono.just(testCustomer), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateCustomerNotFound() {
        Customer testCustomer = getTestCustomer();
        testCustomer.setCustomerName("Cafe het Pleintje not found");

        webTestClient
                .mutateWith(mockOAuth2Login())
                .put().uri(CustomerController.CUSTOMER_PATH_ID, 999)
                .body(Mono.just(testCustomer), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void deleteCustomer() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete().uri(CustomerController.CUSTOMER_PATH_ID, 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void patchCustomerNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .patch().uri(CustomerController.CUSTOMER_PATH_ID, 999)
                .body(Mono.just(getTestCustomer()), CustomerDTO.class)
                .header("Content-Type","application/json")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void deleteCustomerNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete().uri(CustomerController.CUSTOMER_PATH_ID, 999)
                .exchange()
                .expectStatus().isNotFound();
    }

    private static Customer getTestCustomer(){
        return Customer.builder()
                .customerName("Cafe het Pleintje")
                .build();
    }
}