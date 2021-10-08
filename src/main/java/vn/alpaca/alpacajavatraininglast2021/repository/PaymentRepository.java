package vn.alpaca.alpacajavatraininglast2021.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Payment;

public interface PaymentRepository extends
        JpaRepository<Payment, Integer>,
        JpaSpecificationExecutor<Payment> {

    @Query(
            "SELECT payment " +
                    "FROM Payment payment " +
                    "JOIN payment.claimRequest request " +
                    "JOIN request.customer customer " +
                    "WHERE request.id = ?1 AND customer.idCardNumber = ?2"
    )
    Page<Payment> findAllByRequestIdAndCustomerIdCard(
            int requestId,
            String idCardNumber,
            Pageable pageable
    );
}
