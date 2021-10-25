package vn.alpaca.contractservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.contractservice.object.wrapper.request.ContractFilter;
import vn.alpaca.contractservice.object.wrapper.request.ContractRequest;
import vn.alpaca.contractservice.object.wrapper.response.ContractResponse;
import vn.alpaca.contractservice.service.ContractService;
import vn.alpaca.dto.wrapper.SuccessResponse;
import vn.alpaca.util.ExtractParam;

import java.util.Optional;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService service;

    @GetMapping
    @PreAuthorize("hasAuthority('CONTRACT_READ')")
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

    @PreAuthorize("hasAuthority('CONTRACT_READ')")
    @GetMapping("/{contractId}")
    public SuccessResponse<ContractResponse> getContractById(
            @PathVariable("contractId") int id
    ) {
        ContractResponse responseData = service.findContractById(id);

        return new SuccessResponse<>(responseData);
    }

    @PreAuthorize("hasAuthority('CONTRACT_CREATE')")
    @PostMapping
    public SuccessResponse<ContractResponse> createNewContract(
            @RequestBody ContractRequest requestData
    ) {
        ContractResponse responseData = service.createContract(requestData);

        return new SuccessResponse<>(responseData);
    }

    @PreAuthorize("hasAuthority('CONTRACT_UPDATE')")
    @PutMapping(value = "/{contractId}")
    public SuccessResponse<ContractResponse> updateContract(
            @PathVariable("contractId") int id,
            @RequestBody ContractRequest requestData
    ) {

        ContractResponse responseData = service.updateContract(id, requestData);

        return new SuccessResponse<>(responseData);
    }

    @PreAuthorize("hasAuthority('CONTRACT_DELETE')")
    @PatchMapping(value = "/{contractId}/activate")
    public SuccessResponse<Boolean> activateContract(
            @PathVariable("contractId") int id
    ) {
        service.activateContract(id);

        return new SuccessResponse<>(true);
    }

    @PreAuthorize("hasAuthority('CONTRACT_DELETE')")
    @PatchMapping(value = "/{contractId}/deactivate")
    public SuccessResponse<Boolean> deactivateContract(
            @PathVariable("contractId") int id
    ) {
        service.deactivateContract(id);

        return new SuccessResponse<>(true);
    }
}
