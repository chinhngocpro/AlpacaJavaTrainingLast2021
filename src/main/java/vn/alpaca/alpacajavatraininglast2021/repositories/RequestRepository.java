package vn.alpaca.alpacajavatraininglast2021.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.alpaca.alpacajavatraininglast2021.objects.entities.Request;

import java.util.Collection;

public interface RequestRepository extends JpaRepository<Request, Integer> {

    Collection<Request> findAllByNameContains(String name);

    Page<Request> findAllByNameContains(String name, Pageable pageable);

    Collection<Request> findAllByDescriptionContains(String description);

    Page<Request>
    findAllByDescriptionContains(String description, Pageable pageable);

    Collection<Request> findAllByCustomerId(int customerId);

    Page<Request>
    findAllByCustomerId(int customerId, Pageable pageable);

    Collection<Request> findAllByEmployeeInChargeId(int userId);

    Page<Request>
    findAllByEmployeeInChargeId(int userId, Pageable pageable);

    Collection<Request> findAllByStatus(String status);

    Page<Request>
    findAllByStatus(String status, Pageable pageable);
}

