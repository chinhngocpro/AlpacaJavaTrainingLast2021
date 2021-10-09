package vn.alpaca.alpacajavatraininglast2021.controller.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.CustomerDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Customer;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.CustomerMapper;
import vn.alpaca.alpacajavatraininglast2021.object.request.customer.CustomerFilter;
import vn.alpaca.alpacajavatraininglast2021.object.request.customer.CustomerForm;
import vn.alpaca.alpacajavatraininglast2021.object.response.SuccessResponse;
import vn.alpaca.alpacajavatraininglast2021.service.CustomerService;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil.getPageable;
import static vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil.getSort;

@RestController
@RequestMapping(
        value = "/api/user/customers",
        produces = "application/json"
)
public class CustomerController {

    private final CustomerService service;
    private final CustomerMapper mapper;

    public CustomerController(CustomerService service,
                              CustomerMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    @GetMapping
    public SuccessResponse<Page<CustomerDTO>> getAllCustomers(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody CustomerFilter filter
    ) {
        Sort sort = getSort(sortBy);
        Pageable pageable = getPageable(pageNumber, pageSize, sort);

        Page<CustomerDTO> dtoPage = new PageImpl<>(
                service.findAllCustomers(
                                filter,
                                pageable
                        )
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    @GetMapping("/{customerId}")
    public SuccessResponse<CustomerDTO> getCustomerById(
            @PathVariable("customerId") int id
    ) {
        CustomerDTO dto =
                mapper.convertToDTO(service.findCustomerById(id));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_CREATE')")
    @PostMapping(consumes = "application/json")
    public SuccessResponse<CustomerDTO> createNewCustomer(
            @RequestBody CustomerForm formData
    ) {
        Customer customer = mapper.convertToEntity(formData);

        CustomerDTO dto =
                mapper.convertToDTO(service.saveCustomer(customer));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_UPDATE')")
    @PutMapping(
            value = "/{customerId}",
            consumes = "application/json"
    )
    public SuccessResponse<CustomerDTO> updateCustomer(
            @PathVariable("customerId") int id,
            @RequestBody CustomerForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        Customer customer = service.findCustomerById(id);
        NullAwareBeanUtil.getInstance().copyProperties(customer, formData);

        CustomerDTO dto =
                mapper.convertToDTO(service.saveCustomer(customer));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_DELETE')")
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
