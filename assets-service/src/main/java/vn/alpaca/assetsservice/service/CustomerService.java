package vn.alpaca.assetsservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.assetsservice.object.entity.Customer;
import vn.alpaca.assetsservice.object.entity.Customer_;
import vn.alpaca.assetsservice.object.wrapper.request.customer.CustomerFilter;
import vn.alpaca.assetsservice.repository.CustomerRepository;
import vn.alpaca.response.exception.AccessDeniedException;
import vn.alpaca.response.exception.ResourceNotFoundException;

import java.util.Date;

import static vn.alpaca.assetsservice.service.CustomerSpecification.getCustomerSpecification;

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
        // TODO: verify email/phone number (by OTP for example)
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

final class CustomerSpecification {

    public static Specification<Customer>
    getCustomerSpecification(CustomerFilter filter) {
        return Specification
                .where(hasNameContaining(filter.getFullName()))
                .and(isMale(filter.getGender()))
                .and(hasIdCardNumber(filter.getIdCardNumber()))
                .and(hasEmailContaining(filter.getEmail()))
                .and(hasDobBetween(filter.getDobFrom(), filter.getDobTo()))
                .and(hasAddressContaining(filter.getAddress()))
                .and(isActive(filter.getActive()));
    }

    private static Specification<Customer> hasNameContaining(String fullName) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(fullName) ?
                        builder.conjunction() :
                        builder.like(
                                root.get(Customer_.FULL_NAME),
                                "%" + fullName + "%"
                        );
    }

    private static Specification<Customer> isMale(Boolean isMale) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(isMale) ?
                        builder.conjunction() :
                        builder.equal(root.get(Customer_.GENDER), isMale);
    }

    private static Specification<Customer> hasIdCardNumber(
            String idCardNumber) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(idCardNumber) ?
                        builder.conjunction() :
                        builder.equal(
                                root.get(Customer_.ID_CARD_NUMBER),
                                idCardNumber
                        );
    }

    private static Specification<Customer> hasEmailContaining(String email) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(email) ?
                        builder.conjunction() :
                        builder.like(root.get(Customer_.EMAIL), email);
    }

    private static Specification<Customer> hasDobBetween(Date from, Date to) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(from) && ObjectUtils.isEmpty(to) ?
                        builder.conjunction() :
                        ObjectUtils.isEmpty(from) ?
                                builder.lessThanOrEqualTo(
                                        root.get(Customer_.DATE_OF_BIRTH), to) :
                                ObjectUtils.isEmpty(to) ?
                                        builder.greaterThanOrEqualTo(
                                                root.get(
                                                        Customer_.DATE_OF_BIRTH),
                                                from) :
                                        builder.between(
                                                root.get(
                                                        Customer_.DATE_OF_BIRTH),
                                                from, to);
    }

    private static Specification<Customer> hasAddressContaining(
            String address) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(address) ?
                        builder.conjunction() :
                        builder.like(root.get(Customer_.ADDRESS), address);
    }

    private static Specification<Customer> isActive(Boolean active) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(active) ?
                        builder.conjunction() :
                        builder.equal(root.get(Customer_.ACTIVE), active);
    }
}

