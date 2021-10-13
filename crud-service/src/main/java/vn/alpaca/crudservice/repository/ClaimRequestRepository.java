package vn.alpaca.crudservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import vn.alpaca.crudservice.object.entity.ClaimRequest;


public interface ClaimRequestRepository extends
        JpaRepository<ClaimRequest, Integer>,
        JpaSpecificationExecutor<ClaimRequest> {

    @Query("SELECT c FROM ClaimRequest c WHERE  c.customer.idCardNumber = ?1")
    Page<ClaimRequest>
    findAllByCustomerIdCardNumber(
            String idCardNumber,
            Specification<ClaimRequest> specification,
            Pageable pageable
    );
}

