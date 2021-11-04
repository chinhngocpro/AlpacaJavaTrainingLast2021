package vn.alpaca.contractservice.service.message;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import vn.alpaca.common.dto.response.CustomerResponse;
import vn.alpaca.common.exception.BusinessException;
import vn.alpaca.contractservice.entity.Contract;
import vn.alpaca.contractservice.service.ContractService;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ContractMessageService {

    private final ContractService contractService;

    @RabbitListener(queues = "deactivate_customer_queue")
    public void listenDeactivateCustomer(CustomerResponse response) throws BusinessException {
        log.info("ON LISTEN DEACTIVATE CUSTOMER MESSAGE TO UPDATE CUSTOMER'S CONTRACTS");
        int customerId = response.getId();
        List<Contract> contracts = contractService.findContractsByCustomerId(customerId);
        contracts.forEach(contract -> contractService.deactivateContract(contract.getId()));
    }

    @RabbitListener(queues = "activate_customer_queue")
    public void listenActivateCustomer(CustomerResponse response) throws BusinessException {
//        throw new BusinessException("SIMULATING ERROR HANDLER");
        log.info("ON LISTEN ACTIVATE CUSTOMER MESSAGE TO UPDATE CUSTOMER'S CONTRACTS");
        int customerId = response.getId();
        List<Contract> contracts = contractService.findContractsByCustomerId(customerId);
        contracts.forEach(contract -> contractService.activateContract(contract.getId()));
    }
}
