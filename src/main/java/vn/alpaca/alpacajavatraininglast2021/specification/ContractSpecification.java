package vn.alpaca.alpacajavatraininglast2021.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Contract;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Contract_;

import java.util.Date;

@Component
public final class ContractSpecification {

    public Specification<Contract> hasContractCode(String contractCode) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(contractCode) ?
                        builder.conjunction() :
                        builder.equal(
                                root.get(Contract_.CONTRACT_CODE),
                                contractCode
                        );
    }

    public Specification<Contract> isValid(Boolean isValid) {
        return (root, query, builder) ->
                isValid == null ?
                        builder.conjunction() :
                        builder.lessThan(
                                root.get(Contract_.VALID_TO),
                                new Date()
                        );
    }

    public Specification<Contract> hasMaximumAmountInRange(Double amount) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(amount) ?
                        builder.conjunction() :
                        builder.lessThanOrEqualTo(
                                root.get(Contract_.MAXIMUM_AMOUNT),
                                amount
                        );
    }

    public Specification<Contract> hasRemainingAmountInRange(Double amount) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(amount) ?
                        builder.conjunction() :
                        builder.lessThanOrEqualTo(
                                root.get(Contract_.REMAINING_AMOUNT),
                                amount
                        );
    }

    public Specification<Contract> isActive(Boolean active) {
        return (root, query, builder) ->
                active == null ?
                        builder.conjunction() :
                        builder.equal(
                                root.get(Contract_.ACTIVE),
                                active
                        );
    }

    public Specification<Contract> hasAcceptableHospital(Integer hospitalId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(hospitalId) ?
                        builder.conjunction() :
                        builder.isMember(
                                hospitalId,
                                root.get(Contract_.ACCEPTABLE_HOSPITAL_IDS)
                        );
    }

    public Specification<Contract> hasAcceptableAccident(Integer accidentId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(accidentId) ?
                        builder.conjunction() :
                        builder.isMember(
                                accidentId,
                                root.get(Contract_.ACCEPTABLE_ACCIDENT_IDS)
                        );
    }
}
