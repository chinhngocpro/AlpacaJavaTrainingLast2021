package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Payment;
import vn.alpaca.alpacajavatraininglast2021.repository.PaymentRepository;
import vn.alpaca.alpacajavatraininglast2021.specification.PaymentSpecification;

import java.util.Date;

@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentSpecification spec;

    public PaymentService(PaymentRepository repository,
                          PaymentSpecification spec) {
        this.repository = repository;
        this.spec = spec;
    }


    public Page<Payment> findAllPayments(
            Double minAmount,
            Double maxAmount,
            Date fromDate,
            Date toDate,
            Pageable pageable
    ) {
        Specification<Payment> conditions = Specification
                .where(spec.hasAmountBetween(minAmount, maxAmount))
                .and(spec.hasDateBetween(fromDate, toDate));

        return repository.findAll(conditions, pageable);
    }


    public Payment findPaymentById(int id) {
        return repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public Payment findPaymentByRequestIdAndCustomerIdCard(
            int requestId,
            String idCardNumber
    ) {
        return repository
                .findByRequestIdAndCustomerIdCard(requestId, idCardNumber)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public Payment savePayment(Payment payment) {
        return repository.save(payment);
    }
}
