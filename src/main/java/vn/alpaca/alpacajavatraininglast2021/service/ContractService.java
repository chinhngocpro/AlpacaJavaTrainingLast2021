package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Contract;
import vn.alpaca.alpacajavatraininglast2021.object.exception.AccessDeniedException;
import vn.alpaca.alpacajavatraininglast2021.object.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.request.contract.ContractFilter;
import vn.alpaca.alpacajavatraininglast2021.repository.ContractRepository;
import vn.alpaca.alpacajavatraininglast2021.repository.CustomerRepository;

import javax.persistence.EntityExistsException;

import static vn.alpaca.alpacajavatraininglast2021.specification.ContractSpecification.*;

@Service
public class ContractService {

    private final ContractRepository contractRepository;
    private final CustomerRepository customerRepository;

    public ContractService(ContractRepository contractRepository,
                           CustomerRepository customerRepository) {
        this.contractRepository = contractRepository;
        this.customerRepository = customerRepository;
    }

    public Page<Contract> findAllContracts(
            ContractFilter filter,
            Pageable pageable
    ) {
        return contractRepository.findAll(
                getContractSpecification(filter),
                pageable
        );
    }


    public Contract findContractById(int id) {
        return contractRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Contract id not found."
                ));
    }

    public Page<Contract> findContractsByCustomerIdCardNumber(
            String idCardNumber,
            ContractFilter filter,
            Pageable pageable
    ) {
        customerRepository.findByIdCardNumber(idCardNumber)
                .orElseThrow(() -> new AccessDeniedException(
                        "Customer identity does not exist."
                ));

        return contractRepository.findAllByCustomerIdCardNumber(
                idCardNumber,
                getContractSpecification(filter),
                pageable
        );
    }

    public Contract saveContract(Contract contract) {
        // To make sure Contract Code is always uppercase
        contract.setContractCode(contract.getContractCode().toUpperCase());

        boolean existingContractCode = contractRepository
                .existsByContractCode(contract.getContractCode());
        if (existingContractCode) {
            throw new EntityExistsException("Contract code is already exists.");
        }

        return contractRepository.save(contract);
    }

    public void activateContract(int contractId) {
        Contract contract = findContractById(contractId);
        contract.setActive(true);
        contractRepository.save(contract);
    }

    public void deactivateContract(int contractId) {
        Contract contract = findContractById(contractId);
        contract.setActive(false);
        contractRepository.save(contract);
    }



}
