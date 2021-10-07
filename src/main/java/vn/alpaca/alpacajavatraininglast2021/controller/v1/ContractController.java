package vn.alpaca.alpacajavatraininglast2021.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.ContractDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.ClaimRequest;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Contract;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Customer;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.ContractMapper;
import vn.alpaca.alpacajavatraininglast2021.service.ContractService;
import vn.alpaca.alpacajavatraininglast2021.service.CustomerService;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;
import vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil;
import vn.alpaca.alpacajavatraininglast2021.wrapper.request.contract.ContractForm;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.SuccessResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping(
        value = "/api/v1/contracts",
        produces = "application/json"
)
public class ContractController {

    private final ContractService contractService;
    private final CustomerService customerService;
    private final ContractMapper mapper;
    private final NullAwareBeanUtil notNullUtil;
    private final RequestParamUtil paramUtil;

    public ContractController(ContractService contractService,
                              CustomerService customerService,
                              ContractMapper mapper,
                              NullAwareBeanUtil notNullUtil,
                              RequestParamUtil paramUtil) {
        this.contractService = contractService;
        this.customerService = customerService;
        this.mapper = mapper;
        this.notNullUtil = notNullUtil;
        this.paramUtil = paramUtil;
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
            @RequestParam(value = "contract-code", required = false)
                    Optional<String> contractCode,
            @RequestParam(value = "is-valid", required = false)
                    Optional<Boolean> isValid,
            @RequestParam(value = "max-amount", required = false)
                    Optional<Double> maximumAmount,
            @RequestParam(value = "remain-amount", required = false)
                    Optional<Double> remainingAmount,
            @RequestParam(value = "active", required = false)
                    Optional<Boolean> active,
            @RequestParam(value = "hospital-id", required = false)
                    Optional<Integer> hospitalId,
            @RequestParam(value = "accident-id", required = false)
                    Optional<Integer> accidentId
    ) {
        Sort sort = paramUtil.getSort(sortBy);

        Pageable pageable = paramUtil.getPageable(pageNumber, pageSize, sort);

        Page<ContractDTO> dtoPage = new PageImpl<>(
                contractService.findAllContracts(
                                contractCode.orElse(null),
                                isValid.orElse(null),
                                maximumAmount.orElse(null),
                                remainingAmount.orElse(null),
                                active.orElse(null),
                                hospitalId.orElse(null),
                                accidentId.orElse(null),
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

    @PreAuthorize("hasAuthority('CONTRACT_CREATE')")
    @PostMapping(consumes = "application/json")
    public SuccessResponse<ContractDTO> createNewContract(
            @RequestBody ContractForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        Contract contract = new Contract();
        notNullUtil.copyProperties(contract, formData);

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
        notNullUtil.copyProperties(contract, formData);

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
