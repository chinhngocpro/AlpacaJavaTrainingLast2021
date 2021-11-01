package vn.alpaca.userservice.repository.jpa.spec;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import vn.alpaca.common.dto.request.UserFilter;
import vn.alpaca.userservice.entity.jpa.User;

import java.util.Date;

public final class UserSpec {

  public static Specification<User> getUserSpec(UserFilter filter) {
    return Specification.where(hasUsername(filter.getUsername()))
        .and(hasFullNameContaining(filter.getFullName()))
        .and(hasGender(filter.getGender()))
        .and(hasIdCard(filter.getIdCardNumber()))
        .and(hasDobBetween(filter.getFrom(), filter.getTo()))
        .and(hasAddressContaining(filter.getAddress()))
        .and(isActive(filter.getActive()));
  }

  private static Specification<User> hasUsername(String username) {
    return ((root, query, builder) ->
        ObjectUtils.isEmpty(username)
            ? builder.conjunction()
            : builder.equal(root.get("username"), username));
  }

  private static Specification<User> hasFullNameContaining(String fullName) {
    return ((root, query, builder) ->
        ObjectUtils.isEmpty(fullName)
            ? builder.conjunction()
            : builder.like(root.get("fullName"), fullName));
  }

  private static Specification<User> hasGender(Boolean gender) {
    return ((root, query, builder) ->
        ObjectUtils.isEmpty(gender)
            ? builder.conjunction()
            : builder.equal(root.get("gender"), gender));
  }

  private static Specification<User> hasIdCard(String idCardNumber) {
    return ((root, query, builder) ->
        ObjectUtils.isEmpty(idCardNumber)
            ? builder.conjunction()
            : builder.equal(root.get("idCardNumber"), idCardNumber));
  }

  private static Specification<User> hasDobBetween(Date from, Date to) {
    return ((root, query, builder) ->
        ObjectUtils.isEmpty(from) && ObjectUtils.isEmpty(to)
            ? builder.conjunction()
            : ObjectUtils.isEmpty(from)
                ? builder.lessThanOrEqualTo(root.get("dateOfBirth"), to)
                : ObjectUtils.isEmpty(to)
                    ? builder.greaterThanOrEqualTo(root.get("dateOfBirth"), from)
                    : builder.between(root.get("dateOfBirth"), from, to));
  }

  private static Specification<User> hasAddressContaining(String address) {
    return (root, query, builder) ->
        ObjectUtils.isEmpty(address)
            ? builder.conjunction()
            : builder.like(root.get("address"), address);
  }

  private static Specification<User> isActive(Boolean active) {
    return (root, query, builder) ->
        ObjectUtils.isEmpty(active)
            ? builder.conjunction()
            : builder.equal(root.get("active"), active);
  }
}
