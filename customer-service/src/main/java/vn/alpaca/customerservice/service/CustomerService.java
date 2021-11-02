package vn.alpaca.customerservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import vn.alpaca.common.dto.request.CustomerFilter;
import vn.alpaca.common.dto.request.CustomerRequest;
import vn.alpaca.common.exception.ResourceNotFoundException;
import vn.alpaca.customerservice.entity.Customer;
import vn.alpaca.customerservice.mapper.CustomerMapper;
import vn.alpaca.customerservice.repository.CustomerRepository;
import vn.alpaca.customerservice.repository.spec.CustomerSpec;

import javax.persistence.EntityExistsException;

@Service
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerRepository repository;
  private final CustomerMapper mapper;

  public Page<Customer> findAllCustomers(CustomerFilter filter) {
    return repository.findAll(
        CustomerSpec.getSpecification(filter), filter.getPagination().getPageAndSort());
  }

  public Customer findCustomerById(int id) {
    Customer customer =
        repository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    if (!customer.isActive()) {
      throw new AccessDeniedException("This customer was disabled.");
    }
    return customer;
  }

  public Customer findCustomerByIdCardNumber(String idCardNumber) {
    Customer customer =
        repository
            .findByIdCardNumber(idCardNumber)
            .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    if (!customer.isActive()) {
      throw new AccessDeniedException("This customer was disabled.");
    }
    return customer;
  }

  public Customer createCustomer(CustomerRequest requestData) {
    // TODO: verify email/phone number (by OTP for example)
    Customer customer = mapper.convertToEntity(requestData);

    if (repository.existsByIdCardNumber(customer.getIdCardNumber())) {
      throw new EntityExistsException(
          "Id card number " + requestData.getIdCardNumber() + " already exists");
    }

    return repository.save(customer);
  }

  public Customer updateCustomer(int id, CustomerRequest requestData) {
    Customer customer = findCustomerById(id);
    mapper.updateCustomer(customer, requestData);

    if (repository.existsByIdCardNumber(customer.getIdCardNumber())) {
      throw new EntityExistsException(
          "Id card number " + requestData.getIdCardNumber() + " already exists");
    }

    return repository.save(customer);
  }

  public void activateCustomer(int customerId) {
    Customer customer = findCustomerById(customerId);
    customer.setActive(true);
    repository.save(customer);
  }

  public void deactivateCustomer(int customerId) {
    Customer customer = findCustomerById(customerId);
    customer.setActive(false);
    repository.save(customer);
  }
}
