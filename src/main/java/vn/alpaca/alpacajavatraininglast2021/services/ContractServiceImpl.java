package vn.alpaca.alpacajavatraininglast2021.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exceptions.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.objects.entities.Contract;
import vn.alpaca.alpacajavatraininglast2021.repositories.ContractRepository;

import java.util.Collection;

@Service
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;

    public ContractServiceImpl(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    public Collection<Contract> findAllContracts() {
        return contractRepository.findAll();
    }

    @Override
    public Page<Contract> findAllContracts(Pageable pageable) {
        return contractRepository.findAll(pageable);
    }

    @Override
    public Collection<Contract> findActiveContracts() {
        return contractRepository.findAllByActiveIsTrue();
    }

    @Override
    public Page<Contract> findActiveContracts(Pageable pageable) {
        return contractRepository.findAllByActiveIsTrue(pageable);
    }

    @Override
    public Contract findContractsByContractCode(String contractCode) {
        return contractRepository.findByContractCode(contractCode)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    @Override
    public Contract findContractById(int id) {
        return contractRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    @Override
    public Contract findContractByCustomerId(int customerId) {
        return contractRepository.findByCustomerId(customerId)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    @Override
    public Contract saveContract(Contract contract) {
        return contractRepository.save(contract);
    }

    @Override
    public void activateContract(int contractId) {
        // Option 1
        Contract contract = findContractById(contractId);
        contract.setActive(true);
        saveContract(contract);

        // Option 2
//        contractRepository.activate(contractId);
    }

    @Override
    public void deactivateContract(int contractId) {
        Contract contract = findContractById(contractId);
        contract.setActive(false);
        saveContract(contract);

        // Option 2
//        contractRepository.deactivate(contractId);
    }
}
