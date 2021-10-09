package vn.alpaca.alpacajavatraininglast2021.controller.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.ContractDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Contract;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Customer;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.ContractMapper;
import vn.alpaca.alpacajavatraininglast2021.object.request.contract.ContractFilter;
import vn.alpaca.alpacajavatraininglast2021.object.request.contract.ContractForm;
import vn.alpaca.alpacajavatraininglast2021.object.response.SuccessResponse;
import vn.alpaca.alpacajavatraininglast2021.service.ContractService;
import vn.alpaca.alpacajavatraininglast2021.service.CustomerService;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil.getPageable;
import static vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil.getSort;

@RestController
@RequestMapping(
        value = "/api/user/contracts",
        produces = "application/json"
)
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

    @PreAuthorize("hasAuthority('CONTRACT_READ')")
    @GetMapping
    public SuccessResponse<Page<ContractDTO>> getAllContracts(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody ContractFilter filter
    ) {
        Sort sort = getSort(sortBy);

        Pageable pageable = getPageable(pageNumber, pageSize, sort);

        Page<ContractDTO> dtoPage = new PageImpl<>(
                contractService.findAllContracts(filter, pageable)
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @PreAuthorize("hasAuthority('CONTRACT_READ')")
    @GetMapping("/{contractId}")
    public SuccessResponse<ContractDTO> getContractById(
            @PathVariable("contractId") int id
    ) {
        ContractDTO dto =
                mapper.convertToDTO(contractService.findContractById(id));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('CONTRACT_CREATE')")
    @PostMapping(consumes = "application/json")
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

    @PreAuthorize("hasAuthority('CONTRACT_UPDATE')")
    @PutMapping(value = "/{contractId}")
    public SuccessResponse<ContractDTO> updateContract(
            @PathVariable("contractId") int id,
            @RequestBody ContractForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        Contract contract = contractService.findContractById(id);
        NullAwareBeanUtil.getInstance().copyProperties(contract, formData);

        if (formData.getCustomerId() != null) {
            Customer customer = customerService
                    .findCustomerById(formData.getCustomerId());
            contract.setCustomer(customer);
        }

        ContractDTO dto =
                mapper.convertToDTO(contractService.saveContract(contract));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('CONTRACT_UPDATE')")
    @PatchMapping(value = "/{contractId}/activate")
    public SuccessResponse<Boolean> activateContract(
            @PathVariable("contractId") int id
    ) {
        contractService.activateContract(id);

        return new SuccessResponse<>(true);
    }


    @PreAuthorize("hasAuthority('CONTRACT_UPDATE')")
    @PatchMapping(value = "/{contractId}/deactivate")
    public SuccessResponse<Boolean> deactivateContract(
            @PathVariable("contractId") int id
    ) {
        contractService.deactivateContract(id);

        return new SuccessResponse<>(true);
    }
}

