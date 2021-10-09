package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Customer;
import vn.alpaca.alpacajavatraininglast2021.object.exception.AccessDeniedException;
import vn.alpaca.alpacajavatraininglast2021.object.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.request.customer.CustomerFilter;
import vn.alpaca.alpacajavatraininglast2021.repository.CustomerRepository;

import static vn.alpaca.alpacajavatraininglast2021.specification.CustomerSpecification.getCustomerSpecification;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Page<Customer> findAllCustomers(
            CustomerFilter filter,
            Pageable pageable
    ) {
        return repository.findAll(
                getCustomerSpecification(filter),
                pageable
        );
    }

    public Customer findCustomerById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer id not found"
                ));
    }

    public Customer findCustomerByIdCardNumber(String idCardNumber) {
        return repository.findByIdCardNumber(idCardNumber)
                .orElseThrow(() -> new AccessDeniedException(
                        "Customer identity does not exist."
                ));
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
