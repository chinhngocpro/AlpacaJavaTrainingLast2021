package vn.alpaca.assetsservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.alpaca.assetsservice.object.entity.Customer;

import java.util.Optional;

public interface CustomerRepository extends
        JpaRepository<Customer, Integer>,
        JpaSpecificationExecutor<Customer> {
    Optional<Customer> findByIdCardNumber(String idCardNumber);
}
