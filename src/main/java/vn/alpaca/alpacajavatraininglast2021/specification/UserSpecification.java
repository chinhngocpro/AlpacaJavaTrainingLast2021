package vn.alpaca.alpacajavatraininglast2021.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User_;
import vn.alpaca.alpacajavatraininglast2021.object.request.user.UserFilter;

import java.util.Date;

public final class UserSpecification {

    public static Specification<User> getUserSpecification(UserFilter filter) {
        return Specification
                .where(hasUsername(filter.getUsername()))
                .and(hasNameContaining(filter.getFullName()))
                .and(isMale(filter.getGender()))
                .and(hasIdCardNumber(filter.getIdCardNumber()))
                .and(hasEmailContaining(filter.getEmail()))
                .and(hasDobBetween(filter.getFrom(), filter.getTo()))
                .and(hasAddressContaining(filter.getAddress()))
                .and(isActive(filter.getActive()));
    }

    private static Specification<User> hasUsername(String username) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(username) ?
                        builder.conjunction() :
                        builder.equal(
                                root.get(User_.USERNAME),
                                username
                        );
    }

    private static Specification<User> hasNameContaining(String fullName) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(fullName) ?
                        builder.conjunction() :
                        builder.like(
                                root.get(User_.FULL_NAME),
                                "%" + fullName + "%"
                        );
    }

    private static Specification<User> isMale(Boolean isMale) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(isMale) ?
                        builder.conjunction() :
                        builder.equal(root.get(User_.GENDER), isMale);
    }

    private static Specification<User> hasIdCardNumber(String idCardNumber) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(idCardNumber) ?
                        builder.conjunction() :
                        builder.equal(
                                root.get(User_.ID_CARD_NUMBER),
                                idCardNumber
                        );
    }

    private static Specification<User> hasEmailContaining(String email) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(email) ?
                        builder.conjunction() :
                        builder.like(root.get(User_.EMAIL), email);
    }

    private static Specification<User> hasDobBetween(Date from, Date to) {
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

    private static Specification<User> hasAddressContaining(String address) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(address) ?
                        builder.conjunction() :
                        builder.like(root.get(User_.ADDRESS), address);
    }

    private static Specification<User> isActive(Boolean active) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(active) ?
                        builder.conjunction() :
                        builder.equal(root.get(User_.ACTIVE), active);
    }


}
