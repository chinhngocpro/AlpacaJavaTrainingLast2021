package vn.alpaca.assetsservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.assetsservice.object.entity.Contract;
import vn.alpaca.assetsservice.object.entity.Contract_;
import vn.alpaca.assetsservice.object.wrapper.request.contract.ContractFilter;
import vn.alpaca.assetsservice.repository.ContractRepository;
import vn.alpaca.assetsservice.repository.CustomerRepository;
import vn.alpaca.response.exception.AccessDeniedException;
import vn.alpaca.response.exception.ResourceNotFoundException;

import javax.persistence.EntityExistsException;
import java.util.Date;

import static vn.alpaca.assetsservice.service.ContractSpecification.getContractSpecification;

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

final class ContractSpecification {

    public static Specification<Contract>
    getContractSpecification(ContractFilter filter) {
        return Specification
                .where(hasContractCode(filter.getContractCode()))
                .and(isValid(filter.getIsValid()))
                .and(hasMaximumAmountInRange(filter.getMaximumAmount()))
                .and(hasRemainingAmountInRange(filter.getRemainingAmount()))
                .and(isActive(filter.getActive()))
                .and(hasAcceptableHospital(filter.getHospitalId()))
                .and(hasAcceptableAccident(filter.getAccidentId()));
    }

    private static Specification<Contract> hasContractCode(
            String contractCode) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(contractCode) ?
                        builder.conjunction() :
                        builder.equal(
                                root.get(Contract_.CONTRACT_CODE),
                                contractCode
                        );
    }

    private static Specification<Contract> isValid(Boolean isValid) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(isValid) ?
                        builder.conjunction() :
                        builder.lessThan(
                                root.get(Contract_.VALID_TO),
                                new Date()
                        );
    }

    private static Specification<Contract> hasMaximumAmountInRange(
            Double amount) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(amount) ?
                        builder.conjunction() :
                        builder.lessThanOrEqualTo(
                                root.get(Contract_.MAXIMUM_AMOUNT),
                                amount
                        );
    }

    private static Specification<Contract> hasRemainingAmountInRange(
            Double amount) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(amount) ?
                        builder.conjunction() :
                        builder.lessThanOrEqualTo(
                                root.get(Contract_.REMAINING_AMOUNT),
                                amount
                        );
    }

    private static Specification<Contract> isActive(Boolean active) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(active) ?
                        builder.conjunction() :
                        builder.equal(
                                root.get(Contract_.ACTIVE),
                                active
                        );
    }

    private static Specification<Contract> hasAcceptableHospital(
            Integer hospitalId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(hospitalId) ?
                        builder.conjunction() :
                        builder.isMember(
                                hospitalId,
                                root.get(Contract_.ACCEPTABLE_HOSPITAL_IDS)
                        );
    }

    private static Specification<Contract> hasAcceptableAccident(
            Integer accidentId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(accidentId) ?
                        builder.conjunction() :
                        builder.isMember(
                                accidentId,
                                root.get(Contract_.ACCEPTABLE_ACCIDENT_IDS)
                        );
    }
}

