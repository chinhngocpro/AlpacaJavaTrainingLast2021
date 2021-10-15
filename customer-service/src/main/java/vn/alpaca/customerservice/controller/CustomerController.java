package vn.alpaca.customerservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.customerservice.object.wrapper.request.CustomerFilter;
import vn.alpaca.customerservice.object.wrapper.request.CustomerRequest;
import vn.alpaca.customerservice.object.wrapper.response.CustomerResponse;
import vn.alpaca.customerservice.service.CustomerService;
import vn.alpaca.response.wrapper.SuccessResponse;
import vn.alpaca.util.ExtractParam;

import java.util.Optional;

@RestController
@RequestMapping(value = "/")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    @GetMapping
    public SuccessResponse<Page<CustomerResponse>> getAllCustomers(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody Optional<CustomerFilter> filter
    ) {
        Sort sort = ExtractParam.getSort(sortBy);
        Pageable pageable =
                ExtractParam.getPageable(pageNumber, pageSize, sort);

        Page<CustomerResponse> responseData = service
                .findAllCustomers(filter.orElse(new CustomerFilter()),
                        pageable);

        return new SuccessResponse<>(responseData);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    @GetMapping("/{customerId}")
    public SuccessResponse<CustomerResponse> getCustomerById(
            @PathVariable("customerId") int id
    ) {
        CustomerResponse responseData = service.findCustomerById(id);

        return new SuccessResponse<>(responseData);
    }

    @GetMapping("/search/id-card/{idCardNumber}")
    public SuccessResponse<CustomerResponse> getCustomerByIdCardNumber(
            @PathVariable String idCardNumber
    ) {
        CustomerResponse responseData = service
                .findCustomerByIdCardNumber(idCardNumber);

        return new SuccessResponse<>(responseData);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_CREATE')")
    @PostMapping
    public SuccessResponse<CustomerResponse> createNewCustomer(
            @RequestBody CustomerRequest formData
    ) {
        CustomerResponse responseData = service.createCustomer(formData);

        return new SuccessResponse<>(responseData);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_UPDATE')")
    @PutMapping(value = "/{customerId}")
    public SuccessResponse<CustomerResponse> updateCustomer(
            @PathVariable("customerId") int id,
            @RequestBody CustomerRequest formData
    ) {

        CustomerResponse responseData = service.updateCustomer(id, formData);

        return new SuccessResponse<>(responseData);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_DELETE')")
    @PatchMapping(value = "/{customerId}/activate")
    public SuccessResponse<Boolean> activateCustomer(
            @PathVariable("customerId") int id
    ) {
        service.activateCustomer(id);

        return new SuccessResponse<>(true);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_DELETE')")
    @PatchMapping(value = "/{customerId}/deactivate")
    public SuccessResponse<Boolean> deactivateCustomer(
            @PathVariable("customerId") int id
    ) {
        service.deactivateCustomer(id);

        return new SuccessResponse<>(true);
    }
}
