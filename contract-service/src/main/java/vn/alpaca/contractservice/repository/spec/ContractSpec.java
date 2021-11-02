package vn.alpaca.contractservice.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import vn.alpaca.common.dto.request.ContractFilter;
import vn.alpaca.contractservice.entity.Contract;

import java.util.Date;

public final class ContractSpec {

  public static Specification<Contract> getSpecification(ContractFilter filter) {
    return Specification.where(hasContractCode(filter.getContractCode()))
        .and(isValid(filter.getIsValid()))
        .and(hasMaximumAmountInRange(filter.getMaximumAmount()))
        .and(hasRemainingAmountInRange(filter.getRemainingAmount()))
        .and(isActive(filter.getActive()))
        .and(hasAcceptableHospital(filter.getHospitalId()))
        .and(hasAcceptableAccident(filter.getAccidentId()));
  }

  private static Specification<Contract> hasContractCode(String contractCode) {
    return (root, query, builder) ->
        ObjectUtils.isEmpty(contractCode)
            ? builder.conjunction()
            : builder.equal(root.get("contractCode"), contractCode);
  }

  private static Specification<Contract> isValid(Boolean isValid) {
    return (root, query, builder) ->
        ObjectUtils.isEmpty(isValid)
            ? builder.conjunction()
            : builder.lessThan(root.get("validTo"), new Date());
  }

  private static Specification<Contract> hasMaximumAmountInRange(Double amount) {
    return (root, query, builder) ->
        ObjectUtils.isEmpty(amount)
            ? builder.conjunction()
            : builder.lessThanOrEqualTo(root.get("maximumAmount"), amount);
  }

  private static Specification<Contract> hasRemainingAmountInRange(Double amount) {
    return (root, query, builder) ->
        ObjectUtils.isEmpty(amount)
            ? builder.conjunction()
            : builder.lessThanOrEqualTo(root.get("remainingAmount"), amount);
  }

  private static Specification<Contract> isActive(Boolean active) {
    return (root, query, builder) ->
        ObjectUtils.isEmpty(active)
            ? builder.conjunction()
            : builder.equal(root.get("active"), active);
  }

  private static Specification<Contract> hasAcceptableHospital(Integer hospitalId) {
    return (root, query, builder) ->
        ObjectUtils.isEmpty(hospitalId)
            ? builder.conjunction()
            : builder.isMember(hospitalId, root.get("acceptableHospitalIds"));
  }

  private static Specification<Contract> hasAcceptableAccident(Integer accidentId) {
    return (root, query, builder) ->
        ObjectUtils.isEmpty(accidentId)
            ? builder.conjunction()
            : builder.isMember(accidentId, root.get("acceptableAccidentIds"));
  }
}
