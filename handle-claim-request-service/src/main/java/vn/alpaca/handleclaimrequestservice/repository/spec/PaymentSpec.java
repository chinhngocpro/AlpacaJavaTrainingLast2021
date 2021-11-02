package vn.alpaca.handleclaimrequestservice.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import vn.alpaca.common.dto.request.PaymentFilter;
import vn.alpaca.handleclaimrequestservice.entity.Payment;

import java.util.Date;

public final class PaymentSpec {

    public static Specification<Payment> getSpecification(PaymentFilter filter) {
        return Specification
                .where(hasAmountBetween(filter.getMinAmount(), filter.getMaxAmount()))
                .and(hasDateBetween(filter.getFromDate(), filter.getToDate()));
    }

    private static Specification<Payment> hasAmountBetween(Double min, Double max) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(min) && ObjectUtils.isEmpty(max)
                ? builder.conjunction()
                : ObjectUtils.isEmpty(min)
                  ? builder.lessThanOrEqualTo(root.get("amount"), max)
                  : ObjectUtils.isEmpty(max)
                    ? builder.greaterThanOrEqualTo(root.get("amount"), min)
                    : builder.between(root.get("amount"), min, max);
    }

    private static Specification<Payment> hasDateBetween(Date from, Date to) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(from) && ObjectUtils.isEmpty(to)
                ? builder.conjunction()
                : ObjectUtils.isEmpty(from)
                  ? builder.lessThanOrEqualTo(root.get("paymentDate"), to)
                  : ObjectUtils.isEmpty(to)
                    ? builder.greaterThanOrEqualTo(root.get("paymentDate"), from)
                    : builder.between(root.get("paymentDate"), from, to);
    }
}
