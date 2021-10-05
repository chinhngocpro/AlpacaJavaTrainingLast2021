package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Customer;
import vn.alpaca.alpacajavatraininglast2021.repository.CustomerRepository;

import java.util.Collection;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Collection<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Page<Customer> findAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    public Customer findCustomerById(int id) {
        return customerRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public Collection<Customer> findCustomerByNameContains(String fullName) {
        return customerRepository.findByFullNameContains(fullName);
    }

    public Page<Customer>
    findCustomerByNameContains(String fullName, Pageable pageable) {
        return customerRepository.findByFullNameContains(fullName, pageable);
    }

    public Customer findCustomerByIdCard(String idCardNumber) {
        return customerRepository.findByIdCardNumber(idCardNumber)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
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
