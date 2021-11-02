package vn.alpaca.customerservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.common.dto.request.CustomerFilter;
import vn.alpaca.common.dto.request.CustomerRequest;
import vn.alpaca.common.dto.response.CustomerResponse;
import vn.alpaca.common.dto.wrapper.AbstractResponse;
import vn.alpaca.common.dto.wrapper.SuccessResponse;
import vn.alpaca.customerservice.entity.Customer;
import vn.alpaca.customerservice.mapper.CustomerMapper;
import vn.alpaca.customerservice.service.CustomerService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    private final CustomerService service;
    private final CustomerMapper mapper;

    public CustomerController(CustomerService service, CustomerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    @GetMapping
    AbstractResponse getAllCustomers(@RequestBody Optional<CustomerFilter> filter) {
        Page<CustomerResponse> response =
                service
                        .findAllCustomers(filter.orElse(new CustomerFilter()))
                        .map(mapper::customerToCustomerResponse);

        return new SuccessResponse<>(response);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    @GetMapping("/{customerId}")
    AbstractResponse getCustomerById(@PathVariable int customerId) {
        Customer customer = service.findCustomerById(customerId);
        CustomerResponse response = mapper.customerToCustomerResponse(customer);

        return new SuccessResponse<>(response);
    }

    @GetMapping("/_search/id-card/{idCardNumber}")
    AbstractResponse getCustomerByIdCardNumber(@PathVariable String idCardNumber) {
        Customer customer = service.findCustomerByIdCardNumber(idCardNumber);
        CustomerResponse response = mapper.customerToCustomerResponse(customer);

        return new SuccessResponse<>(response);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_CREATE')")
    @PostMapping
    AbstractResponse createNewCustomer(@RequestBody @Valid CustomerRequest formData) {
        Customer customer = service.createCustomer(formData);
        CustomerResponse response = mapper.customerToCustomerResponse(customer);

        return new SuccessResponse<>(response);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_UPDATE')")
    @PutMapping(value = "/{customerId}")
    AbstractResponse updateCustomer(
            @PathVariable("customerId") int id, @RequestBody @Valid CustomerRequest formData) {
        Customer customer = service.updateCustomer(id, formData);
        CustomerResponse response = mapper.customerToCustomerResponse(customer);

        return new SuccessResponse<>(response);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_DELETE')")
    @PatchMapping(value = "/{customerId}/activate")
    AbstractResponse activateCustomer(@PathVariable("customerId") int id) {
        service.activateCustomer(id);

        return new SuccessResponse<>(true);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_DELETE')")
    @PatchMapping(value = "/{customerId}/deactivate")
    AbstractResponse deactivateCustomer(@PathVariable("customerId") int id) {
        service.deactivateCustomer(id);

        return new SuccessResponse<>(true);
    }
}
