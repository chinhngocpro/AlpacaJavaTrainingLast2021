package vn.alpaca.contractservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import vn.alpaca.common.dto.request.ContractFilter;
import vn.alpaca.common.dto.request.ContractRequest;
import vn.alpaca.common.exception.ResourceNotFoundException;
import vn.alpaca.contractservice.client.CustomerFeignClient;
import vn.alpaca.contractservice.entity.Contract;
import vn.alpaca.contractservice.mapper.ContractMapper;
import vn.alpaca.contractservice.repository.ContractRepository;
import vn.alpaca.contractservice.repository.spec.ContractSpec;

import javax.persistence.EntityExistsException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository repository;
    private final ContractMapper mapper;
    private final CustomerFeignClient customerFeignClient;

    public Page<Contract> findAllContracts(ContractFilter filter) {
        return repository.findAll(
                ContractSpec.getSpecification(filter), filter.getPagination().getPageAndSort());
    }

    public Contract findContractById(int id) {
        Contract contract = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found contract id: " + id));
        if (!contract.isActive()) {
            throw new AccessDeniedException("This contract is disabled");
        }

        return contract;
    }

    public Page<Contract> findContractsByCustomerId(String idCardNumber, ContractFilter filter) {
        int customerId = customerFeignClient.getByIdCardNumber(idCardNumber).getData().getId();

        return repository.findAllByCustomerId(customerId,
                                              ContractSpec.getSpecification(filter),
                                              filter.getPagination().getPageAndSort());
    }

    public List<Contract> findContractsByCustomerId(Integer customerId) {
        return repository.findAllByCustomerId(customerId);
    }

    public Contract createContract(ContractRequest requestData) {
        Contract contract = mapper.createContract(requestData);
        contract.setContractCode(contract.getContractCode().toUpperCase());

        boolean existingContractCode = repository.existsByContractCode(contract.getContractCode());
        if (existingContractCode) {
            throw new EntityExistsException("Contract code is already exists.");
        }

        return contract;
    }

    public Contract updateContract(int id, ContractRequest requestData) {
        Contract contract = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Not found contract id: " + id));
        mapper.updateContract(contract, requestData);
        contract.setContractCode(contract.getContractCode().toUpperCase());

        boolean existingContractCode = repository.existsByContractCode(contract.getContractCode());
        if (existingContractCode) {
            throw new EntityExistsException("Contract code is already exists.");
        }

        return repository.save(contract);
    }

    public Contract activateContract(int contractId) {
        Contract contract = repository.findById(contractId).orElseThrow(
                () -> new ResourceNotFoundException("Not found contract id: " + contractId));
        contract.setActive(true);
        return repository.save(contract);
    }

    public Contract deactivateContract(int contractId) {
        Contract contract = repository.findById(contractId).orElseThrow(
                () -> new ResourceNotFoundException("Not found contract id: " + contractId));

        contract.setActive(false);
        return repository.save(contract);
    }
}
