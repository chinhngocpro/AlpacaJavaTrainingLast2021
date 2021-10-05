package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exception.AccessDeniedException;
import vn.alpaca.alpacajavatraininglast2021.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.entity.AnalyzedReceipt;
import vn.alpaca.alpacajavatraininglast2021.repository.ReceiptRepository;

import java.util.Collection;

@Service
public class AnalyzedReceiptService {

    private final ReceiptRepository receiptRepository;

    public AnalyzedReceiptService(ReceiptRepository receiptRepository) {
        this.receiptRepository = receiptRepository;
    }

    public Collection<AnalyzedReceipt> findAllReceipts() {
        return receiptRepository.findAll();
    }

    public Page<AnalyzedReceipt> findAllReceipts(Pageable pageable) {
        return receiptRepository.findAll(pageable);
    }

    public Collection<AnalyzedReceipt> findValidReceipts() {
        return receiptRepository.findAllByValidIsTrue();
    }

    public Page<AnalyzedReceipt> findValidReceipts(Pageable pageable) {
        return receiptRepository.findAllByValidIsTrue(pageable);
    }

    public Collection<AnalyzedReceipt> findReceiptsByUserId(int userId) {
        return receiptRepository.findAllByAnalyzerId(userId);
    }

    public Page<AnalyzedReceipt>
    findReceiptsByUserId(int userId, Pageable pageable) {
        return receiptRepository.findAllByAnalyzerId(userId, pageable);
    }

    public AnalyzedReceipt findReceiptById(int id) {
        return receiptRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public AnalyzedReceipt findReceiptByClaimRequestId(int requestId) {
        return receiptRepository.findByClaimRequestId(requestId)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public AnalyzedReceipt saveReceipt(
            AnalyzedReceipt receipt) {
        return receiptRepository.save(receipt);
    }

    public AnalyzedReceipt
    saveInChargeReceipt(int userId, AnalyzedReceipt receipt) {
        if (receipt.getAnalyzer().getId() != userId) {
            throw new AccessDeniedException(); // TODO: implement exception message
        }

        return receiptRepository.save(receipt);
    }

    public void validateReceipt(int receiptId) {
        AnalyzedReceipt
                receipt = findReceiptById(receiptId);
        receipt.setValid(true);
        saveReceipt(receipt);
    }

    public void invalidateReceipt(int receiptId) {
        AnalyzedReceipt
                receipt = findReceiptById(receiptId);
        receipt.setValid(false);
        saveReceipt(receipt);
    }
}
