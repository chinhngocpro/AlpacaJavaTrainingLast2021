package vn.alpaca.alpacajavatraininglast2021.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.alpaca.alpacajavatraininglast2021.objects.entities.Customer;

import java.util.Collection;

public interface CustomerService {

    // 1. View all customers' info
    Collection<Customer> findAllCustomers();

    Page<Customer> findAllCustomers(Pageable pageable);

    // 2. View specific customers (apply searching)

    Customer findCustomerById(int id);

    Collection<Customer> findCustomerByNameContains(String fullName);
    Page<Customer>
    findCustomerByNameContains(String fullName, Pageable pageable);

    Customer findCustomerByIdCard(String idCardNumber);

    // 3. Create new customer / Edit customer info

    Customer saveCustomer(Customer customer);

    // 4. Activate/deactivate customer
    void activateCustomer(int customerId);

    void deactivateCustomer(int customerId);
}
