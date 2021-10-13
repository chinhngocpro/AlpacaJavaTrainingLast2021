package vn.alpaca.claimrequestservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.alpaca.claimrequestservice.object.entity.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByIdCardNumber(String idCardNumber);
}
