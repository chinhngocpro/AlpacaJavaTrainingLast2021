package vn.alpaca.alpacajavatraininglast2021.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Payment;

import java.util.Optional;

public interface PaymentRepository extends
        JpaRepository<Payment, Integer>,
        JpaSpecificationExecutor<Payment> {

    @Query("SELECT o FROM Payment o " +
            "WHERE o.claimRequest.id = :requestId " +
            "AND o.claimRequest.customer.idCardNumber = :idCardNumber")
    Optional<Payment>
    findByRequestIdAndCustomerIdCard(int requestId, String idCardNumber);
}
