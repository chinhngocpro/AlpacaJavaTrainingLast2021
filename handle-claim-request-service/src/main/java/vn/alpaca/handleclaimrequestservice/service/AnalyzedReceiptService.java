package vn.alpaca.handleclaimrequestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.alpaca.common.dto.request.AnalyzedReceiptFilter;
import vn.alpaca.common.dto.request.AnalyzedReceiptRequest;
import vn.alpaca.common.exception.ResourceNotFoundException;
import vn.alpaca.handleclaimrequestservice.entity.AnalyzedReceipt;
import vn.alpaca.handleclaimrequestservice.entity.ClaimRequest;
import vn.alpaca.handleclaimrequestservice.mapper.AnalyzedReceiptMapper;
import vn.alpaca.handleclaimrequestservice.repository.AnalyzedReceiptRepository;
import vn.alpaca.handleclaimrequestservice.repository.spec.AnalyzedReceiptSpec;

@Service
@RequiredArgsConstructor
public class AnalyzedReceiptService {

    private final AnalyzedReceiptRepository repository;
    private final AnalyzedReceiptMapper mapper;

    private final ClaimRequestService claimRequestService;

    public Page<AnalyzedReceipt> findAllReceipts(AnalyzedReceiptFilter filter) {

        return repository.findAll(AnalyzedReceiptSpec.getSpecification(filter),
                                  filter.getPagination().getPageAndSort());
    }

    public AnalyzedReceipt findReceiptById(int id) {
        return repository.findById(id)
                         .orElseThrow(() -> new ResourceNotFoundException("Receipt not found"));
    }

    public AnalyzedReceipt createReceipt(AnalyzedReceiptRequest requestData) {
        AnalyzedReceipt receipt = mapper.createReceipt(requestData);

        ClaimRequest claimRequest = claimRequestService.findRequestById(requestData.getClaimRequestId());
        receipt.setClaimRequest(claimRequest);

        return receipt;
    }

    public AnalyzedReceipt updateReceipt(int id, AnalyzedReceiptRequest requestData) {
        AnalyzedReceipt receipt = findReceiptById(id);
        mapper.updateReceipt(receipt, requestData);

        ClaimRequest claimRequest = claimRequestService.findRequestById(requestData.getClaimRequestId());
        receipt.setClaimRequest(claimRequest);

        return repository.save(receipt);
    }

    public AnalyzedReceipt updateInChargeReceipt(int userId, AnalyzedReceipt receipt) {
        // TODO: call user service to validate user before save

        return repository.save(receipt);
    }

    public void validateReceipt(int receiptId) {
        AnalyzedReceipt receipt = findReceiptById(receiptId);
        receipt.setValid(true);
        repository.save(receipt);
    }

    public void invalidateReceipt(int receiptId) {
        AnalyzedReceipt receipt = findReceiptById(receiptId);
        receipt.setValid(false);
        repository.save(receipt);
    }
}

