package vn.alpaca.alpacajavatraininglast2021.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.alpaca.alpacajavatraininglast2021.object.entity.ClaimRequest;

import java.util.Collection;

public interface ClaimRequestRepository extends JpaRepository<ClaimRequest, Integer> {

    Collection<ClaimRequest> findAllByTitleContains(String name);

    Page<ClaimRequest> findAllByTitleContains(String name, Pageable pageable);

    Collection<ClaimRequest> findAllByDescriptionContains(String description);

    Page<ClaimRequest>
    findAllByDescriptionContains(String description, Pageable pageable);

    Collection<ClaimRequest> findAllByCustomerId(int customerId);

    Page<ClaimRequest>
    findAllByCustomerId(int customerId, Pageable pageable);

    Collection<ClaimRequest> findAllByEmployeeInChargeId(int userId);

    Page<ClaimRequest>
    findAllByEmployeeInChargeId(int userId, Pageable pageable);

    Collection<ClaimRequest> findAllByStatus(String status);

    Page<ClaimRequest>
    findAllByStatus(String status, Pageable pageable);
}

