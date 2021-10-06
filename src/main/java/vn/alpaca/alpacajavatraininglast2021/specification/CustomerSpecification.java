package vn.alpaca.alpacajavatraininglast2021.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Customer;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Customer_;

import java.util.Date;

@Component
public final class CustomerSpecification {

    public Specification<Customer> hasNameContaining(String fullName) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(fullName) ?
                        builder.conjunction() :
                        builder.like(
                                root.get(Customer_.FULL_NAME),
                                "%" + fullName + "%"
                        );
    }

    public Specification<Customer> isMale(Boolean isMale) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(isMale) ?
                        builder.conjunction() :
                        builder.equal(root.get(Customer_.GENDER), isMale);
    }

    public Specification<Customer> hasIdCardNumber(String idCardNumber) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(idCardNumber) ?
                        builder.conjunction() :
                        builder.equal(
                                root.get(Customer_.ID_CARD_NUMBER),
                                idCardNumber
                        );
    }

    public Specification<Customer> hasEmailContaining(String email) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(email) ?
                        builder.conjunction() :
                        builder.like(root.get(Customer_.EMAIL), email);
    }

    public Specification<Customer> hasDobBetween(Date from, Date to) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(from) && ObjectUtils.isEmpty(to) ?
                        builder.conjunction() :
                        ObjectUtils.isEmpty(from) ?
                                builder.lessThanOrEqualTo(
                                        root.get(Customer_.DATE_OF_BIRTH), to) :
                                ObjectUtils.isEmpty(to) ?
                                        builder.greaterThanOrEqualTo(
                                                root.get(
                                                        Customer_.DATE_OF_BIRTH),
                                                from) :
                                        builder.between(
                                                root.get(
                                                        Customer_.DATE_OF_BIRTH),
                                                from, to);
    }

    public Specification<Customer> hasAddressContaining(String address) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(address) ?
                        builder.conjunction() :
                        builder.like(root.get(Customer_.ADDRESS), address);
    }

    public Specification<Customer> isActive(Boolean active) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(active) ?
                        builder.conjunction() :
                        builder.equal(root.get(Customer_.ACTIVE), active);
    }
}
