package vn.alpaca.alpacajavatraininglast2021.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.alpaca.alpacajavatraininglast2021.objects.entities.Payment;

import java.util.Collection;
import java.util.Date;

public interface PaymentService {

    // 1. View all available payments
    Collection<Payment> findAllPayments();

    Page<Payment> findAllPayments(Pageable pageable);

    // 2. View specific payments (apply searching)
    Collection<Payment> findPaymentsByUserId(int userId);

    Page<Payment> findPaymentsByUserId(int userId, Pageable pageable);

    Collection<Payment> findPaymentsByDateBetween(Date from, Date to);

    Page<Payment> findPaymentsByDateBetween(Date from, Date to, Pageable pageable);

    Payment findPaymentById(int id);

    Payment findPaymentByRequestId(int requestId);

    // 3. Create new payment / Edit payment info
    Payment savePayment(Payment payment);
}
