package vn.alpaca.alpacajavatraininglast2021.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exceptions.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.objects.entities.Customer;
import vn.alpaca.alpacajavatraininglast2021.repositories.CustomerRepository;

import java.util.Collection;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Collection<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Page<Customer> findAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer findCustomerById(int id) {
        return customerRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    @Override
    public Collection<Customer> findCustomerByNameContains(String fullName) {
        return customerRepository.findByFullNameContains(fullName);
    }

    @Override
    public Page<Customer>
    findCustomerByNameContains(String fullName, Pageable pageable) {
        return customerRepository.findByFullNameContains(fullName, pageable);
    }


    @Override
    public Customer findCustomerByIdCard(String idCardNumber) {
        return customerRepository.findByIdCardNumber(idCardNumber)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void activateCustomer(int customerId) {
        Customer customer = findCustomerById(customerId);
        customer.setActive(true);
        saveCustomer(customer);
    }

    @Override
    public void deactivateCustomer(int customerId) {
        Customer customer = findCustomerById(customerId);
        customer.setActive(false);
        saveCustomer(customer);
    }
}
