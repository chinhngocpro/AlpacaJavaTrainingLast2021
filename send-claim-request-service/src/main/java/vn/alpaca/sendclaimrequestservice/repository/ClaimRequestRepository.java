package vn.alpaca.sendclaimrequestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.alpaca.sendclaimrequestservice.entity.ClaimRequest;

public interface ClaimRequestRepository extends JpaRepository<ClaimRequest, Integer> {


}
