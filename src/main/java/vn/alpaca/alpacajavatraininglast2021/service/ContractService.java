package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Contract;
import vn.alpaca.alpacajavatraininglast2021.repository.ContractRepository;

import java.util.Collection;

@Service
public class ContractService {

    private final ContractRepository contractRepository;

    public ContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Collection<Contract> findAllContracts() {
        return contractRepository.findAll();
    }

    public Page<Contract> findAllContracts(Pageable pageable) {
        return contractRepository.findAll(pageable);
    }

    public Collection<Contract> findActiveContracts() {
        return contractRepository.findAllByActiveIsTrue();
    }

    public Page<Contract> findActiveContracts(Pageable pageable) {
        return contractRepository.findAllByActiveIsTrue(pageable);
    }

    public Contract findContractByContractCode(String contractCode) {
        return contractRepository.findByContractCode(contractCode)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public Contract findContractById(int id) {
        return contractRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public Contract findContractByCustomerId(int customerId) {
        return contractRepository.findByCustomerId(customerId)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public Contract saveContract(Contract contract) {
        return contractRepository.save(contract);
    }

    public void activateContract(int contractId) {
        // Option 1
        Contract contract = findContractById(contractId);
        contract.setActive(true);
        saveContract(contract);

        // Option 2
//        contractRepository.activate(contractId);
    }

    public void deactivateContract(int contractId) {
        Contract contract = findContractById(contractId);
        contract.setActive(false);
        saveContract(contract);

        // Option 2
//        contractRepository.deactivate(contractId);
    }
}
