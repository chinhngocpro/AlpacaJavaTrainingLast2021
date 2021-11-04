package vn.alpaca.contractservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import vn.alpaca.contractservice.entity.Contract;

import java.util.List;

public interface ContractRepository
        extends JpaRepository<Contract, Integer>, JpaSpecificationExecutor<Contract> {

    @Query("SELECT o FROM Contract o WHERE o.customerId = ?1")
    Page<Contract> findAllByCustomerId(int customerId,
                                       Specification<Contract> specification,
                                       Pageable pageable);

    List<Contract> findAllByCustomerId(int customerId);

    boolean existsByContractCode(String contractCode);
}
