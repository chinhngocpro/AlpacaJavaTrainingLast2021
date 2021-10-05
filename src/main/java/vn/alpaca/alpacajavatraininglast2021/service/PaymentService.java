package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Payment;
import vn.alpaca.alpacajavatraininglast2021.repository.PaymentRepository;

import java.util.Collection;
import java.util.Date;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Collection<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    public Page<Payment> findAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }

    public Collection<Payment> findPaymentsByUserId(int userId) {
        return paymentRepository.findByAccountantId(userId);
    }

    public Page<Payment> findPaymentsByUserId(int userId, Pageable pageable) {
        return paymentRepository.findByAccountantId(userId, pageable);
    }

    public Collection<Payment> findPaymentsByDateBetween(Date from, Date to) {
        return paymentRepository.findAllByPaymentDateBetween(from, to);
    }

    public Page<Payment> findPaymentsByDateBetween(Date from, Date to,
                                                   Pageable pageable) {
        return paymentRepository
                .findAllByPaymentDateBetween(from, to, pageable);
    }

    public Payment findPaymentById(int id) {
        return paymentRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public Payment findPaymentByClaimRequestId(int requestId) {
        return paymentRepository.findByClaimRequestId(requestId)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}
