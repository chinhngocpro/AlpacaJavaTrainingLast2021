package vn.alpaca.alpacajavatraininglast2021.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Payment;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Collection<Payment> findByAccountantId(int userId);

    Page<Payment> findByAccountantId(int userId, Pageable pageable);

    Collection<Payment> findAllByPaymentDateBetween(Date from, Date to);

    Page<Payment>
    findAllByPaymentDateBetween(Date from, Date to, Pageable pageable);

    Optional<Payment> findByClaimRequestId(int requestId);
}
