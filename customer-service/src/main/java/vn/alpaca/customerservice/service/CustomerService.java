package vn.alpaca.customerservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.customerservice.object.entity.Customer;
import vn.alpaca.customerservice.object.entity.Customer_;
import vn.alpaca.customerservice.object.mapper.CustomerMapper;
import vn.alpaca.customerservice.object.wrapper.request.CustomerFilter;
import vn.alpaca.customerservice.object.wrapper.request.CustomerRequest;
import vn.alpaca.customerservice.object.wrapper.response.CustomerResponse;
import vn.alpaca.customerservice.repository.CustomerRepository;
import vn.alpaca.response.exception.AccessDeniedException;
import vn.alpaca.response.exception.ResourceNotFoundException;
import vn.alpaca.util.NullAware;

import java.util.Date;
import java.util.Optional;

import static vn.alpaca.customerservice.service.CustomerSpecification.getCustomerSpecification;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    public Page<CustomerResponse> findAllCustomers(
            CustomerFilter filter,
            Pageable pageable
    ) {
        return repository.findAll(
                getCustomerSpecification(filter),
                pageable
        ).map(mapper::convertToResponseModel);
    }

    public CustomerResponse findCustomerById(int id) {
        Customer customer = preHandleGetObject(repository.findById(id));
        return mapper.convertToResponseModel(customer);
    }

    public CustomerResponse findCustomerByIdCardNumber(String idCardNumber) {
        Customer customer =
                preHandleGetObject(repository.findByIdCardNumber(idCardNumber));
        if (!customer.isActive()) {
            throw new AccessDeniedException(
                    "This customer was disabled."
            );
        }
        return mapper.convertToResponseModel(customer);
    }

    public CustomerResponse createCustomer(CustomerRequest requestData) {
        // TODO: verify email/phone number (by OTP for example)
        Customer customer = mapper.convertToEntity(requestData);
        return mapper.convertToResponseModel(repository.save(customer));
    }

    public CustomerResponse updateCustomer(int id, CustomerRequest formData) {
        Customer customer = preHandleGetObject(repository.findById(id));

        try {
            NullAware.getInstance().copyProperties(customer, formData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapper.convertToResponseModel(repository.save(customer));
    }

    public void activateCustomer(int customerId) {
        Customer customer = preHandleGetObject(repository.findById(customerId));
        customer.setActive(true);
        repository.save(customer);
    }

    public void deactivateCustomer(int customerId) {
        Customer customer = preHandleGetObject(repository.findById(customerId));
        customer.setActive(false);
        repository.save(customer);
    }

    private Customer preHandleGetObject(Optional<Customer> optional) {
        return optional.orElseThrow(() -> new ResourceNotFoundException(
                "Customer not found"
        ));
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

