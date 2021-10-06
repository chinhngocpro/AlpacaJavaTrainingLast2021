package vn.alpaca.alpacajavatraininglast2021.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.CustomerDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Customer;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.CustomerMapper;
import vn.alpaca.alpacajavatraininglast2021.service.CustomerService;
import vn.alpaca.alpacajavatraininglast2021.util.DateUtil;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;
import vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil;
import vn.alpaca.alpacajavatraininglast2021.wrapper.request.customer.CustomerForm;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.SuccessResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService service;
    private final CustomerMapper mapper;
    private final NullAwareBeanUtil notNullUtil;
    private final DateUtil dateUtil;
    private final RequestParamUtil paramUtil;

    public CustomerController(CustomerService service,
                              CustomerMapper mapper,
                              NullAwareBeanUtil notNullUtil,
                              DateUtil dateUtil,
                              RequestParamUtil paramUtil) {
        this.service = service;
        this.mapper = mapper;
        this.notNullUtil = notNullUtil;
        this.dateUtil = dateUtil;
        this.paramUtil = paramUtil;
    }

    @GetMapping(
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<Page<CustomerDTO>> getAllCustomers(
            @RequestParam("page") Optional<Integer> pageNumber,
            @RequestParam("size") Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestParam(value = "full-name", required = false)
                    Optional<String> fullName,
            @RequestParam(value = "gender", required = false)
                    Optional<Boolean> isMale,
            @RequestParam(value = "id-card", required = false)
                    Optional<String> idCardNumber,
            @RequestParam(value = "email", required = false)
                    Optional<String> email,
            @RequestParam(value = "dob-from", required = false)
                    Optional<String> dobFrom,
            @RequestParam(value = "dob-to", required = false)
                    Optional<String> dobTo,
            @RequestParam(value = "address", required = false)
                    Optional<String> address,
            @RequestParam(value = "active", required = false)
                    Optional<Boolean> active
    ) {
        Sort sort = paramUtil.getSort(sortBy);
        Pageable pageable = paramUtil.getPageable(pageNumber, pageSize, sort);

        Page<CustomerDTO> dtoPage = new PageImpl<>(
                service.findAllCustomers(
                                fullName.orElse(null),
                                isMale.orElse(null),
                                idCardNumber.orElse(null),
                                email.orElse(null),
                                dateUtil.convertStringToDate(dobFrom.orElse(null)),
                                dateUtil.convertStringToDate(dobTo.orElse(null)),
                                address.orElse(null),
                                active.orElse(null),
                                pageable
                        )
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @GetMapping(
            value = "/{customerId}",
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<CustomerDTO> getCustomerById(
            @PathVariable("customerId") int id
    ) {

        CustomerDTO dto =
                mapper.convertToDTO(service.findCustomerById(id));

        return new SuccessResponse<>(dto);
    }

    @PostMapping(
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<CustomerDTO> createNewCustomer(
            @RequestBody CustomerForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        Customer customer = new Customer();
        notNullUtil.copyProperties(customer, formData);

        CustomerDTO dto =
                mapper.convertToDTO(service.saveCustomer(customer));

        return new SuccessResponse<>(dto);
    }

    @PutMapping(
            value = "/{customerId}",
            consumes = "application/json",
            produces = "application/json"
    )
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

    @PatchMapping(
            value = "/{customerId}/activate",
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<Boolean> activateCustomer(
            @PathVariable("customerId") int id
    ) {
        service.activateCustomer(id);

        return new SuccessResponse<>(true);
    }

    @PatchMapping(
            value = "/{customerId}/deactivate",
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<Boolean> deactivateCustomer(
            @PathVariable("customerId") int id
    ) {
        service.deactivateCustomer(id);

        return new SuccessResponse<>(true);
    }
}
