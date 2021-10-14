package vn.alpaca.handleclaimrequestservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import vn.alpaca.handleclaimrequestservice.object.entity.Payment;

public interface PaymentRepository extends
        JpaRepository<Payment, Integer>,
        JpaSpecificationExecutor<Payment> {

    @Query(
            "SELECT payment " +
                    "FROM Payment payment " +
                    "JOIN payment.claimRequest request " +
                    "WHERE request.id = ?1 AND request.customerId = ?2"
    )
    Page<Payment> findAllByRequestIdAndCustomerId(
            int requestId,
            int customerId,
            Specification<Payment> specification,
            Pageable pageable
    );
}
