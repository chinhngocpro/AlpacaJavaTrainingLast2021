package vn.alpaca.claimrequestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.alpaca.claimrequestservice.object.entity.ClaimRequest;

public interface ClaimRequestRepository extends JpaRepository<ClaimRequest, Integer> {


}
