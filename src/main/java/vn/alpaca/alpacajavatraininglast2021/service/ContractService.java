package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Contract;
import vn.alpaca.alpacajavatraininglast2021.repository.ContractRepository;
import vn.alpaca.alpacajavatraininglast2021.specification.ContractSpecification;

import java.util.Date;

@Service
public class ContractService {

    private final ContractRepository repository;
    private final ContractSpecification spec;

    public ContractService(ContractRepository repository,
                           ContractSpecification spec) {
        this.repository = repository;
        this.spec = spec;
    }

    public Page<Contract> findAllContracts(
            String contractCode,
            Boolean isValid,
            Double maximumAmount,
            Double remainingAmount,
            Boolean active,
            Integer hospitalId,
            Integer accidentId,
            Pageable pageable
    ) {
        Specification<Contract> conditions = Specification
                .where(spec.hasContractCode(contractCode))
                .and(spec.isValid(isValid))
                .and(spec.hasMaximumAmountInRange(maximumAmount))
                .and(spec.hasRemainingAmountInRange(remainingAmount))
                .and(spec.isActive(active))
                .and(spec.hasAcceptableHospital(hospitalId))
                .and(spec.hasAcceptableAccident(accidentId));

        return repository.findAll(conditions, pageable);
    }

    public Contract findContractById(int id) {
        return repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public Contract saveContract(Contract contract) {
        return repository.save(contract);
    }

    public void activateContract(int contractId) {
        Contract contract = findContractById(contractId);
        contract.setActive(true);
        saveContract(contract);
    }

    public void deactivateContract(int contractId) {
        Contract contract = findContractById(contractId);
        contract.setActive(false);
        saveContract(contract);
    }
}
