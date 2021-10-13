package vn.alpaca.assetsservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.assetsservice.object.entity.Customer;
import vn.alpaca.assetsservice.object.mapper.CustomerMapper;
import vn.alpaca.assetsservice.object.wrapper.dto.CustomerDTO;
import vn.alpaca.assetsservice.object.wrapper.request.customer.CustomerFilter;
import vn.alpaca.assetsservice.object.wrapper.request.customer.CustomerForm;
import vn.alpaca.assetsservice.service.CustomerService;
import vn.alpaca.response.wrapper.SuccessResponse;
import vn.alpaca.util.ExtractParam;
import vn.alpaca.util.NullAware;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/assets/customers")
public class CustomerController {

    private final CustomerService service;
    private final CustomerMapper mapper;

    public CustomerController(CustomerService service,
                              CustomerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public SuccessResponse<Page<CustomerDTO>> getAllCustomers(
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

        Page<CustomerDTO> dtoPage = new PageImpl<>(
                service.findAllCustomers(
                                filter.orElse(new CustomerFilter()),
                                pageable
                        )
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @GetMapping("/{customerId}")
    public SuccessResponse<CustomerDTO> getCustomerById(
            @PathVariable("customerId") int id
    ) {
        CustomerDTO dto =
                mapper.convertToDTO(service.findCustomerById(id));

        return new SuccessResponse<>(dto);
    }

    @PostMapping
    public SuccessResponse<CustomerDTO> createNewCustomer(
            @RequestBody CustomerForm formData
    ) {
        Customer customer = mapper.convertToEntity(formData);

        CustomerDTO dto =
                mapper.convertToDTO(service.saveCustomer(customer));

        return new SuccessResponse<>(dto);
    }

    @PutMapping(value = "/{customerId}")
    public SuccessResponse<CustomerDTO> updateCustomer(
            @PathVariable("customerId") int id,
            @RequestBody CustomerForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        Customer customer = service.findCustomerById(id);
        NullAware.getInstance().copyProperties(customer, formData);

        CustomerDTO dto =
                mapper.convertToDTO(service.saveCustomer(customer));

        return new SuccessResponse<>(dto);
    }

    @PatchMapping(value = "/{customerId}/activate")
    public SuccessResponse<Boolean> activateCustomer(
            @PathVariable("customerId") int id
    ) {
        service.activateCustomer(id);

        return new SuccessResponse<>(true);
    }

    @PatchMapping(value = "/{customerId}/deactivate")
    public SuccessResponse<Boolean> deactivateCustomer(
            @PathVariable("customerId") int id
    ) {
        service.deactivateCustomer(id);

        return new SuccessResponse<>(true);
    }
}
