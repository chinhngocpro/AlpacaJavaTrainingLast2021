package vn.alpaca.contractservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.contractservice.client.CustomerFeignClient;
import vn.alpaca.contractservice.object.entity.Contract;
import vn.alpaca.contractservice.object.entity.Contract_;
import vn.alpaca.contractservice.object.mapper.ContractMapper;
import vn.alpaca.contractservice.object.wrapper.request.ContractFilter;
import vn.alpaca.contractservice.object.wrapper.request.ContractRequest;
import vn.alpaca.contractservice.object.wrapper.response.ContractResponse;
import vn.alpaca.contractservice.repository.ContractRepository;
import vn.alpaca.response.exception.ResourceNotFoundException;
import vn.alpaca.util.NullAware;

import javax.persistence.EntityExistsException;
import java.util.Date;
import java.util.Optional;

import static vn.alpaca.contractservice.service.ContractSpecification.getContractSpecification;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepository repository;
    private final ContractMapper mapper;
    private final CustomerFeignClient customerFeignClient;

    public Page<ContractResponse> findAllContracts(
            ContractFilter filter,
            Pageable pageable
    ) {
        return repository.findAll(
                getContractSpecification(filter),
                pageable
        ).map(mapper::convertToResponseModel);
    }


    public ContractResponse findContractById(int id) {
        Contract contract = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found contract id: " + id
                ));
        return mapper.convertToResponseModel(contract);
    }

    public Page<ContractResponse> findContractsByCustomerId(
            String idCardNumber,
            ContractFilter filter,
            Pageable pageable
    ) {
        int customerId = customerFeignClient.getByIdCardNumber(idCardNumber)
                .getData()
                .getId();
//        System.out.println(customerFeignClient.getByIdCardNumber(idCardNumber) != null);

        return repository.findAllByCustomerId(
                customerId,
                getContractSpecification(filter),
                pageable
        ).map(mapper::convertToResponseModel);
    }

    public ContractResponse createContract(ContractRequest requestData) {
        Contract contract =
                preHandleSaveObject(mapper.convertToEntity(requestData));
        return mapper.convertToResponseModel(repository.save(contract));
    }

    public ContractResponse
    updateContract(int id, ContractRequest requestData) {
        // To make sure Contract Code is always uppercase
        Contract contract = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Not found contract id: " + id
                ));
        try {
            NullAware.getInstance().copyProperties(contract, requestData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapper.convertToResponseModel(repository.save(contract));
    }

    public void activateContract(int contractId) {
        Contract contract =
                preHandleGetObject(repository.findById(contractId));
        contract.setActive(true);
        repository.save(contract);
    }

    public void deactivateContract(int contractId) {
        Contract contract =
                preHandleGetObject(repository.findById(contractId));

        contract.setActive(false);
        repository.save(contract);
    }

    private Contract preHandleGetObject(Optional<Contract> optional) {
        return optional.orElseThrow(() -> new ResourceNotFoundException(
                "Contract not found"
        ));
    }

    private Contract preHandleSaveObject(Contract contract) {
        contract.setContractCode(contract.getContractCode().toUpperCase());

        boolean existingContractCode = repository
                .existsByContractCode(contract.getContractCode());
        if (existingContractCode) {
            throw new EntityExistsException("Contract code is already exists.");
        }

        return contract;
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
