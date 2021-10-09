package vn.alpaca.alpacajavatraininglast2021.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Payment;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Payment_;
import vn.alpaca.alpacajavatraininglast2021.object.request.payment.PaymentFilter;

import java.util.Date;

public final class PaymentSpecification {

    public static Specification<Payment>
    getPaymentSpecification(PaymentFilter filter) {
        return Specification
                .where(hasAmountBetween(
                        filter.getMinAmount(),
                        filter.getMaxAmount()
                ))
                .and(hasDateBetween(
                        filter.getFromDate(),
                        filter.getToDate()
                ));
    }

    private static Specification<Payment>
    hasAmountBetween(Double min, Double max) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(min) && ObjectUtils.isEmpty(max) ?
                        builder.conjunction() :
                        ObjectUtils.isEmpty(min) ?
                                builder.lessThanOrEqualTo(
                                        root.get(Payment_.AMOUNT),
                                        max
                                ) :
                                ObjectUtils.isEmpty(max) ?
                                        builder.greaterThanOrEqualTo(
                                                root.get(Payment_.AMOUNT),
                                                min
                                        ) :
                                        builder.between(
                                                root.get(Payment_.AMOUNT),
                                                min,
                                                max
                                        );
    }

    private static Specification<Payment>
    hasDateBetween(Date from, Date to) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(from) && ObjectUtils.isEmpty(to) ?
                        builder.conjunction() :
                        ObjectUtils.isEmpty(from) ?
                                builder.lessThanOrEqualTo(
                                        root.get(Payment_.PAYMENT_DATE),
                                        to
                                ) :
                                ObjectUtils.isEmpty(to) ?
                                        builder.greaterThanOrEqualTo(
                                                root.get(Payment_.PAYMENT_DATE),
                                                from
                                        ) :
                                        builder.between(
                                                root.get(Payment_.PAYMENT_DATE),
                                                from,
                                                to
                                        );
    }
}
