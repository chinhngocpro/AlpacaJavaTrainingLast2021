package vn.alpaca.contractservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.contractservice.object.entity.Contract;
import vn.alpaca.contractservice.object.wrapper.request.ContractFilter;
import vn.alpaca.contractservice.object.wrapper.request.ContractRequest;
import vn.alpaca.contractservice.object.wrapper.response.ContractResponse;
import vn.alpaca.contractservice.service.ContractService;
import vn.alpaca.response.wrapper.SuccessResponse;
import vn.alpaca.util.ExtractParam;

import java.util.Optional;

@RestController
@RequestMapping("/")
public class ContractController {

    private final ContractService service;

    public ContractController(ContractService service) {
        this.service = service;
    }

    @GetMapping
    public SuccessResponse<Page<ContractResponse>> getAllContracts(
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

        Page<ContractResponse> responseData = service.findAllContracts(
                filter.orElse(new ContractFilter()),
                pageable
        );

        return new SuccessResponse<>(responseData);
    }

    @GetMapping("/search/customer-id-card/{idCardNumber}")
    public SuccessResponse<Page<ContractResponse>>
    getContractByCustomerIdCard(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @PathVariable String idCardNumber,
            @RequestBody Optional<ContractFilter> filter
    ) {
        Sort sort = ExtractParam.getSort(sortBy);

        Pageable pageable =
                ExtractParam.getPageable(pageNumber, pageSize, sort);

        Page<ContractResponse> responseData =
                service.findContractsByCustomerId(
                        idCardNumber,
                        filter.orElse(new ContractFilter()),
                        pageable
                );
        return new SuccessResponse<>(responseData);
    }

    @GetMapping("/{contractId}")
    public SuccessResponse<ContractResponse> getContractById(
            @PathVariable("contractId") int id
    ) {
        ContractResponse responseData = service.findContractById(id);

        return new SuccessResponse<>(responseData);
    }

    @PostMapping
    public SuccessResponse<ContractResponse> createNewContract(
            @RequestBody ContractRequest requestData
    ) {
        ContractResponse responseData = service.createContract(requestData);

        return new SuccessResponse<>(responseData);
    }

    @PutMapping(value = "/{contractId}")
    public SuccessResponse<ContractResponse> updateContract(
            @PathVariable("contractId") int id,
            @RequestBody ContractRequest requestData
    ) {

        ContractResponse responseData = service.updateContract(id, requestData);

        return new SuccessResponse<>(responseData);
    }

    @PatchMapping(value = "/{contractId}/activate")
    public SuccessResponse<Boolean> activateContract(
            @PathVariable("contractId") int id
    ) {
        service.activateContract(id);

        return new SuccessResponse<>(true);
    }

    @PatchMapping(value = "/{contractId}/deactivate")
    public SuccessResponse<Boolean> deactivateContract(
            @PathVariable("contractId") int id
    ) {
        service.deactivateContract(id);

        return new SuccessResponse<>(true);
    }
}
