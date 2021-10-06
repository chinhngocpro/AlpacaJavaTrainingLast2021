package vn.alpaca.alpacajavatraininglast2021.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Role_;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User_;

import java.util.Date;

@Component
public final class UserSpecification {

    public Specification<User> hasUsername(String username) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(username) ?
                        builder.conjunction() :
                        builder.equal(
                                root.get(User_.USERNAME),
                                username
                        );
    }

    public Specification<User> hasNameContaining(String fullName) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(fullName) ?
                        builder.conjunction() :
                        builder.like(
                                root.get(User_.FULL_NAME),
                                "%" + fullName + "%"
                        );
    }

    public Specification<User> isMale(Boolean isMale) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(isMale) ?
                        builder.conjunction() :
                        builder.equal(root.get(User_.GENDER), isMale);
    }

    public Specification<User> hasIdCardNumber(String idCardNumber) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(idCardNumber) ?
                        builder.conjunction() :
                        builder.equal(
                                root.get(User_.ID_CARD_NUMBER),
                                idCardNumber
                        );
    }

    public Specification<User> hasEmailContaining(String email) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(email) ?
                        builder.conjunction() :
                        builder.like(root.get(User_.EMAIL), email);
    }

    public Specification<User> hasDobBetween(Date from, Date to) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(from) && ObjectUtils.isEmpty(to) ?
                        builder.conjunction() :
                        ObjectUtils.isEmpty(from) ?
                                builder.lessThanOrEqualTo(
                                        root.get(User_.DATE_OF_BIRTH), to) :
                                ObjectUtils.isEmpty(to) ?
                                        builder.greaterThanOrEqualTo(
                                                root.get(User_.DATE_OF_BIRTH),
                                                from) :
                                        builder.between(
                                                root.get(User_.DATE_OF_BIRTH),
                                                from, to);
    }

    public Specification<User> hasAddressContaining(String address) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(address) ?
                        builder.conjunction() :
                        builder.like(root.get(User_.ADDRESS), address);
    }

    public Specification<User> isActive(Boolean active) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(active) ?
                        builder.conjunction() :
                        builder.equal(root.get(User_.ACTIVE), active);
    }

    public Specification<User> hasRoleName(String roleName) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(roleName) ?
                        builder.conjunction() :
                        builder.like(root.get(Role_.NAME), roleName);
    }

}
