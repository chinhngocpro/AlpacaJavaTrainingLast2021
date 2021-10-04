package vn.alpaca.alpacajavatraininglast2021.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exceptions.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.objects.entities.Payment;
import vn.alpaca.alpacajavatraininglast2021.repositories.PaymentRepository;

import java.util.Collection;
import java.util.Date;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Collection<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Page<Payment> findAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable);
    }

    @Override
    public Collection<Payment> findPaymentsByUserId(int userId) {
        return paymentRepository.findByAccountantId(userId);
    }

    @Override
    public Page<Payment> findPaymentsByUserId(int userId, Pageable pageable) {
        return paymentRepository.findByAccountantId(userId, pageable);
    }

    @Override
    public Collection<Payment> findPaymentsByDateBetween(Date from, Date to) {
        return paymentRepository.findAllByPaymentDateBetween(from, to);
    }

    @Override
    public Page<Payment> findPaymentsByDateBetween(Date from, Date to,
                                                   Pageable pageable) {
        return paymentRepository
                .findAllByPaymentDateBetween(from, to, pageable);
    }

    @Override
    public Payment findPaymentById(int id) {
        return paymentRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    @Override
    public Payment findPaymentByRequestId(int requestId) {
        return paymentRepository.findByRequestId(requestId)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}
