package vn.alpaca.customerservice.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import vn.alpaca.common.dto.request.CustomerFilter;
import vn.alpaca.customerservice.entity.Customer;

import java.util.Date;

public final class CustomerSpec {

  public static Specification<Customer> getSpecification(CustomerFilter filter) {
    return Specification.where(hasNameContaining(filter.getFullName()))
        .and(isMale(filter.getGender()))
        .and(hasIdCardNumber(filter.getIdCardNumber()))
        .and(hasEmailContaining(filter.getEmail()))
        .and(hasDobBetween(filter.getDobFrom(), filter.getDobTo()))
        .and(hasAddressContaining(filter.getAddress()))
        .and(isActive(filter.getActive()));
  }

  private static Specification<Customer> hasNameContaining(String fullName) {
    return (root, query, builder) ->
        ObjectUtils.isEmpty(fullName)
            ? builder.conjunction()
            : builder.like(root.get("fullName"), "%" + fullName + "%");
  }

  private static Specification<Customer> isMale(Boolean isMale) {
    return (root, query, builder) ->
        ObjectUtils.isEmpty(isMale)
            ? builder.conjunction()
            : builder.equal(root.get("gender"), isMale);
  }

  private static Specification<Customer> hasIdCardNumber(String idCardNumber) {
    return (root, query, builder) ->
        ObjectUtils.isEmpty(idCardNumber)
            ? builder.conjunction()
            : builder.equal(root.get("idCardNumber"), idCardNumber);
  }

  private static Specification<Customer> hasEmailContaining(String email) {
    return (root, query, builder) ->
        ObjectUtils.isEmpty(email) ? builder.conjunction() : builder.like(root.get("email"), email);
  }

  private static Specification<Customer> hasDobBetween(Date from, Date to) {
    return (root, query, builder) ->
        ObjectUtils.isEmpty(from) && ObjectUtils.isEmpty(to)
            ? builder.conjunction()
            : ObjectUtils.isEmpty(from)
                ? builder.lessThanOrEqualTo(root.get("dateOfBirth"), to)
                : ObjectUtils.isEmpty(to)
                    ? builder.greaterThanOrEqualTo(root.get("dateOfBirth"), from)
                    : builder.between(root.get("dateOfBirth"), from, to);
  }

  private static Specification<Customer> hasAddressContaining(String address) {
    return (root, query, builder) ->
        ObjectUtils.isEmpty(address)
            ? builder.conjunction()
            : builder.like(root.get("address"), address);
  }

  private static Specification<Customer> isActive(Boolean active) {
    return (root, query, builder) ->
        ObjectUtils.isEmpty(active)
            ? builder.conjunction()
            : builder.equal(root.get("active"), active);
  }
}
