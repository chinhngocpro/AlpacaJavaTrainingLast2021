package vn.alpaca.alpacajavatraininglast2021.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.CustomerDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Customer;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.CustomerMapper;
import vn.alpaca.alpacajavatraininglast2021.service.CustomerService;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;
import vn.alpaca.alpacajavatraininglast2021.wrapper.request.customer.CustomerForm;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.SuccessResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping(
        value = "/api/v1/customers",
        consumes = "application/json",
        produces = "application/json"
)
public class CustomerController {

    private final CustomerService service;
    private final CustomerMapper mapper;
    private final NullAwareBeanUtil notNullUtil;

    public CustomerController(CustomerService service,
                              CustomerMapper mapper,
                              NullAwareBeanUtil notNullUtil) {
        this.service = service;
        this.mapper = mapper;
        this.notNullUtil = notNullUtil;
    }

    @PreAuthorize("hasAuthority('CUSTOMER_READ')")
    @GetMapping
    public SuccessResponse<Page<CustomerDTO>> getAllCustomers(
            @RequestParam("page") Optional<Integer> pageNumber,
            @RequestParam("size") Optional<Integer> pageSize
    ) {
        Pageable pageable = Pageable.unpaged();

        if (pageNumber.isPresent()) {
            pageable = PageRequest.of(pageNumber.get(), pageSize.orElse(5));
        }

        Page<CustomerDTO> dtoPage = new PageImpl<>(
                service.findAllCustomers(pageable)
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @GetMapping(value = "/{customerId}")
    public SuccessResponse<CustomerDTO> getCustomerById(
            @PathVariable("customerId") int id
    ) {

        CustomerDTO dto =
                mapper.convertToDTO(service.findCustomerById(id));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_CREATE')")
    @PostMapping
    public SuccessResponse<CustomerDTO> createNewCustomer(
            @RequestBody CustomerForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        Customer customer = new Customer();
        notNullUtil.copyProperties(customer, formData);

        CustomerDTO dto =
                mapper.convertToDTO(service.saveCustomer(customer));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_UPDATE')")
    @PutMapping(value = "/{customerId}")
    public SuccessResponse<CustomerDTO> updateCustomer(
            @PathVariable("customerId") int id,
            @RequestBody CustomerForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        Customer customer = service.findCustomerById(id);
        notNullUtil.copyProperties(customer, formData);

        CustomerDTO dto =
                mapper.convertToDTO(service.saveCustomer(customer));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_UPDATE')")
    @PatchMapping(value = "/{customerId}/activate")
    public SuccessResponse<Boolean> activateCustomer(
            @PathVariable("customerId") int id
    ) {
        service.activateCustomer(id);

        return new SuccessResponse<>(true);
    }

    @PreAuthorize("hasAuthority('CUSTOMER_UPDATE')")
    @PatchMapping(value = "/{customerId}/deactivate")
    public SuccessResponse<Boolean> deactivateCustomer(
            @PathVariable("customerId") int id
    ) {
        service.deactivateCustomer(id);

        return new SuccessResponse<>(true);
    }
}
