package vn.alpaca.alpacajavatraininglast2021.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Contract;

import java.util.Collection;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Integer> {

    Collection<Contract> findAllByActiveIsTrue();

    Page<Contract> findAllByActiveIsTrue(Pageable pageable);

    Optional<Contract> findByContractCode(String contractCode);

    Optional<Contract> findByCustomerId(int customerId);

    @Modifying
    @Query("UPDATE Contract c SET c.active = true WHERE c.id = ?1")
    void activate(int id);

    @Modifying
    @Query("UPDATE Contract c SET c.active = false WHERE c.id = ?1")
    void deactivate(int id);
}
