package vn.alpaca.alpacajavatraininglast2021.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Customer;

import java.util.Collection;
import java.util.Optional;

public interface CustomerRepository extends
        JpaRepository<Customer, Integer>,
        JpaSpecificationExecutor<Customer> {
}
