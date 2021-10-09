package vn.alpaca.alpacajavatraininglast2021.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Contract;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Contract_;
import vn.alpaca.alpacajavatraininglast2021.object.request.contract.ContractFilter;

import java.util.Date;

public final class ContractSpecification {

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

    private static Specification<Contract> hasContractCode(String contractCode) {
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

    private static Specification<Contract> hasMaximumAmountInRange(Double amount) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(amount) ?
                        builder.conjunction() :
                        builder.lessThanOrEqualTo(
                                root.get(Contract_.MAXIMUM_AMOUNT),
                                amount
                        );
    }

    private static Specification<Contract> hasRemainingAmountInRange(Double amount) {
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

    private static Specification<Contract> hasAcceptableHospital(Integer hospitalId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(hospitalId) ?
                        builder.conjunction() :
                        builder.isMember(
                                hospitalId,
                                root.get(Contract_.ACCEPTABLE_HOSPITAL_IDS)
                        );
    }

    private static Specification<Contract> hasAcceptableAccident(Integer accidentId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(accidentId) ?
                        builder.conjunction() :
                        builder.isMember(
                                accidentId,
                                root.get(Contract_.ACCEPTABLE_ACCIDENT_IDS)
                        );
    }
}
