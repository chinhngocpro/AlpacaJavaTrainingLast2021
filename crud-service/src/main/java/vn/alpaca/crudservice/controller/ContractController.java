package vn.alpaca.crudservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.crudservice.object.entity.Contract;
import vn.alpaca.crudservice.object.entity.Customer;
import vn.alpaca.crudservice.object.mapper.ContractMapper;
import vn.alpaca.crudservice.object.wrapper.dto.ContractDTO;
import vn.alpaca.crudservice.object.wrapper.request.contract.ContractFilter;
import vn.alpaca.crudservice.object.wrapper.request.contract.ContractForm;
import vn.alpaca.crudservice.service.ContractService;
import vn.alpaca.crudservice.service.CustomerService;
import vn.alpaca.response.wrapper.SuccessResponse;
import vn.alpaca.util.ExtractParam;
import vn.alpaca.util.NullAware;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/contracts")
public class ContractController {

    private final ContractService contractService;
    private final CustomerService customerService;
    private final ContractMapper mapper;

    public ContractController(ContractService contractService,
                              CustomerService customerService,
                              ContractMapper mapper) {
        this.contractService = contractService;
        this.customerService = customerService;
        this.mapper = mapper;
    }

    @GetMapping
    public SuccessResponse<Page<ContractDTO>> getAllContracts(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody Optional<ContractFilter> filter
    ) {
        Sort sort = ExtractParam.getSort(sortBy);

        Pageable pageable =
                ExtractParam.getPageable(pageNumber, pageSize, sort);

        Page<ContractDTO> dtoPage = new PageImpl<>(
                contractService.findAllContracts(
                                filter.orElse(new ContractFilter()),
                                pageable
                        )
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @GetMapping("/{contractId}")
    public SuccessResponse<ContractDTO> getContractById(
            @PathVariable("contractId") int id
    ) {
        ContractDTO dto =
                mapper.convertToDTO(contractService.findContractById(id));

        return new SuccessResponse<>(dto);
    }

    @PostMapping
    public SuccessResponse<ContractDTO> createNewContract(
            @RequestBody ContractForm formData
    ) {
        Contract contract = mapper.convertToEntity(formData);

        Customer customer = customerService
                .findCustomerById(formData.getCustomerId());
        contract.setCustomer(customer);

        ContractDTO dto =
                mapper.convertToDTO(contractService.saveContract(contract));

        return new SuccessResponse<>(dto);
    }

    @PutMapping(value = "/{contractId}")
    public SuccessResponse<ContractDTO> updateContract(
            @PathVariable("contractId") int id,
            @RequestBody ContractForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        Contract contract = contractService.findContractById(id);
        NullAware.getInstance().copyProperties(contract, formData);

        if (formData.getCustomerId() != null) {
            Customer customer = customerService
                    .findCustomerById(formData.getCustomerId());
            contract.setCustomer(customer);
        }

        ContractDTO dto =
                mapper.convertToDTO(contractService.saveContract(contract));

        return new SuccessResponse<>(dto);
    }

    @PatchMapping(value = "/{contractId}/activate")
    public SuccessResponse<Boolean> activateContract(
            @PathVariable("contractId") int id
    ) {
        contractService.activateContract(id);

        return new SuccessResponse<>(true);
    }

    @PatchMapping(value = "/{contractId}/deactivate")
    public SuccessResponse<Boolean> deactivateContract(
            @PathVariable("contractId") int id
    ) {
        contractService.deactivateContract(id);

        return new SuccessResponse<>(true);
    }
}

