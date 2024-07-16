package nl.hkstwk.spring6reactive.mappers;

import nl.hkstwk.spring6reactive.domain.Customer;
import nl.hkstwk.spring6reactive.domain.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    CustomerDTO customerToCustomerDto(Customer customer);
    Customer customerDtoToCustomer(CustomerDTO customerDTO);
}
