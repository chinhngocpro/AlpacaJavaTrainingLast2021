package vn.alpaca.contractservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.common.dto.request.ContractFilter;
import vn.alpaca.common.dto.request.ContractRequest;
import vn.alpaca.common.dto.response.ContractResponse;
import vn.alpaca.common.dto.wrapper.AbstractResponse;
import vn.alpaca.common.dto.wrapper.SuccessResponse;
import vn.alpaca.contractservice.entity.Contract;
import vn.alpaca.contractservice.mapper.ContractMapper;
import vn.alpaca.contractservice.service.ContractService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/contracts")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService service;
    private final ContractMapper mapper;

    @GetMapping
    @PreAuthorize("hasAuthority('CONTRACT_READ')")
    AbstractResponse getAllContracts(@RequestBody Optional<ContractFilter> filter) {
        Page<Contract> contracts = service.findAllContracts(filter.orElse(new ContractFilter()));
        Page<ContractResponse> response = contracts.map(mapper::convertToResponseModel);

        return new SuccessResponse<>(response);
    }

    @GetMapping("/search/customer-id-card/{idCardNumber}")
    AbstractResponse getContractByCustomerIdCard(
            @PathVariable String idCardNumber, @RequestBody Optional<ContractFilter> filter) {
        Page<Contract> contracts =
                service.findContractsByCustomerId(idCardNumber, filter.orElse(new ContractFilter()));
        Page<ContractResponse> response = contracts.map(mapper::convertToResponseModel);

        return new SuccessResponse<>(response);
    }

    @PreAuthorize("hasAuthority('CONTRACT_READ')")
    @GetMapping("/{contractId}")
    AbstractResponse getContractById(@PathVariable("contractId") int id) {
        Contract contract = service.findContractById(id);
        ContractResponse response = mapper.convertToResponseModel(contract);

        return new SuccessResponse<>(response);
    }

    @PreAuthorize("hasAuthority('CONTRACT_CREATE')")
    @PostMapping
    AbstractResponse createNewContract(@RequestBody @Valid ContractRequest requestData) {
        Contract contract = service.createContract(requestData);
        ContractResponse response = mapper.convertToResponseModel(contract);

        return new SuccessResponse<>(response);
    }

    @PreAuthorize("hasAuthority('CONTRACT_UPDATE')")
    @PutMapping(value = "/{contractId}")
    AbstractResponse updateContract(
            @PathVariable("contractId") int id, @RequestBody @Valid ContractRequest requestData) {
        Contract contract = service.updateContract(id, requestData);
        ContractResponse response = mapper.convertToResponseModel(contract);

        return new SuccessResponse<>(response);
    }

    @PreAuthorize("hasAuthority('CONTRACT_DELETE')")
    @PatchMapping(value = "/{contractId}/activate")
    AbstractResponse activateContract(@PathVariable("contractId") int id) {
        service.activateContract(id);

        return new SuccessResponse<>(true);
    }

    @PreAuthorize("hasAuthority('CONTRACT_DELETE')")
    @PatchMapping(value = "/{contractId}/deactivate")
    AbstractResponse deactivateContract(@PathVariable("contractId") int id) {
        service.deactivateContract(id);

        return new SuccessResponse<>(true);
    }
}
