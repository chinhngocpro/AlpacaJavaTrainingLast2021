package vn.alpaca.crudservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.crudservice.object.entity.Payment;
import vn.alpaca.crudservice.object.entity.Payment_;
import vn.alpaca.crudservice.object.wrapper.request.payment.PaymentFilter;
import vn.alpaca.crudservice.repository.CustomerRepository;
import vn.alpaca.crudservice.repository.PaymentRepository;
import vn.alpaca.response.exception.AccessDeniedException;
import vn.alpaca.response.exception.ResourceNotFoundException;

import java.util.Date;

import static vn.alpaca.crudservice.service.PaymentSpecification.getPaymentSpecification;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          CustomerRepository customerRepository) {
        this.paymentRepository = paymentRepository;
        this.customerRepository = customerRepository;
    }

    public Page<Payment> findAllPayments(
            PaymentFilter filter,
            Pageable pageable
    ) {
        return paymentRepository.findAll(
                getPaymentSpecification(filter),
                pageable
        );
    }


    public Payment findPaymentById(int id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment id not found."
                ));
    }

    public Page<Payment> findPaymentsByRequestIdAndCustomerIdCard(
            int requestId,
            String idCardNumber,
            PaymentFilter filter,
            Pageable pageable
    ) {
        customerRepository.findByIdCardNumber(idCardNumber)
                .orElseThrow(() -> new AccessDeniedException(
                        "Customer identity does not exist."
                ));

        return paymentRepository.findAllByRequestIdAndCustomerIdCard(
                requestId,
                idCardNumber,
                getPaymentSpecification(filter),
                pageable
        );
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }


}

final class PaymentSpecification {

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

