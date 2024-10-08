package nl.hkstwk.spring6reactive.services;

import nl.hkstwk.spring6reactive.domain.CustomerDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public interface CustomerService {
    Flux<CustomerDTO> listCustomers();
    Mono<CustomerDTO> getCustomerById(Integer customerId);
    Mono<CustomerDTO> saveNewCustomer(CustomerDTO customerDTO);
    Mono<CustomerDTO> updateCustomer(Integer customerId, CustomerDTO customerDTO);
    Mono<CustomerDTO> patchCustomer(Integer customerId, CustomerDTO customerDTO);
    Mono<Void> deleteBeer(Integer customerId);
}
