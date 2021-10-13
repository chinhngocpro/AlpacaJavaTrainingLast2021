package vn.alpaca.assetsservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import vn.alpaca.assetsservice.object.entity.Contract;


public interface ContractRepository extends
        JpaRepository<Contract, Integer>,
        JpaSpecificationExecutor<Contract> {

    @Query("SELECT c FROM  Contract c WHERE c.customer.idCardNumber = ?1")
    Page<Contract>
    findAllByCustomerIdCardNumber(String idCardNumber,
                                  Specification<Contract> specification,
                                  Pageable pageable
    );

    boolean existsByContractCode(String contractCode);

}
