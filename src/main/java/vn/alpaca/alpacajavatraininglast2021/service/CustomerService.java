package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Customer;
import vn.alpaca.alpacajavatraininglast2021.repository.CustomerRepository;
import vn.alpaca.alpacajavatraininglast2021.specification.CustomerSpecification;

import java.util.Collection;
import java.util.Date;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerSpecification spec;

    public CustomerService(CustomerRepository repository,
                           CustomerSpecification spec) {
        this.repository = repository;
        this.spec = spec;
    }

    public Page<Customer> findAllCustomers(
            String fullName,
            Boolean isMale,
            String idCardNumber,
            String email,
            Date from,
            Date to,
            String address,
            Boolean active,
            Pageable pageable
    ) {
        Specification<Customer> conditions = Specification
                .where(spec.hasNameContaining(fullName))
                .and(spec.isMale(isMale))
                .and(spec.hasIdCardNumber(idCardNumber))
                .and(spec.hasEmailContaining(email))
                .and(spec.hasDobBetween(from, to))
                .and(spec.hasAddressContaining(address))
                .and(spec.isActive(active));

        return repository.findAll(conditions, pageable);
    }

    public Customer findCustomerById(int id) {
        return repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }


    public Customer saveCustomer(Customer customer) {
        return repository.save(customer);
    }

    public void activateCustomer(int customerId) {
        Customer customer = findCustomerById(customerId);
        customer.setActive(true);
        saveCustomer(customer);
    }

    public void deactivateCustomer(int customerId) {
        Customer customer = findCustomerById(customerId);
        customer.setActive(false);
        saveCustomer(customer);
    }
}
