package vn.alpaca.alpacajavatraininglast2021.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.alpaca.alpacajavatraininglast2021.objects.entities.Contract;

import java.util.Collection;

public interface ContractService {

    // 1. View all contracts
    Collection<Contract> findAllContracts();

    Page<Contract> findAllContracts(Pageable pageable);

    // 2. View specific contracts
    Collection<Contract> findActiveContracts();

    Page<Contract> findActiveContracts(Pageable pageable);

    Page<Contract> findContractsByKeyword(Object keyword, Pageable pageable);

    Contract findContractById(int id);

    // 3. View contract belongs to specific customer
    Contract findContractByCustomerId(int customerId);

    // 4. Create new contract / Edit contract info
    Contract saveContract(Contract contract);

    // 5. Activate/deactive contract
    void activateContract(int contractId);

    void deactivateContract(int contractId);
}
