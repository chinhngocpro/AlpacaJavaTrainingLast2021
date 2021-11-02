package vn.alpaca.contractservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    return repository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found contract id: " + id));
  }

  public Page<Contract> findContractsByCustomerId(String idCardNumber, ContractFilter filter) {
    int customerId = customerFeignClient.getByIdCardNumber(idCardNumber).getData().getId();

    return repository.findAllByCustomerId(
        customerId, ContractSpec.getSpecification(filter), filter.getPagination().getPageAndSort());
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
    Contract contract =
        repository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Not found contract id: " + id));
    mapper.updateContract(contract, requestData);
    contract.setContractCode(contract.getContractCode().toUpperCase());

    boolean existingContractCode = repository.existsByContractCode(contract.getContractCode());
    if (existingContractCode) {
      throw new EntityExistsException("Contract code is already exists.");
    }

    return repository.save(contract);
  }

  public void activateContract(int contractId) {
    Contract contract = findContractById(contractId);
    contract.setActive(true);
    repository.save(contract);
  }

  public void deactivateContract(int contractId) {
    Contract contract = findContractById(contractId);

    contract.setActive(false);
    repository.save(contract);
  }
}
