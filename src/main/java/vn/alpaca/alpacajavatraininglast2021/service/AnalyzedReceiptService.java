package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exception.AccessDeniedException;
import vn.alpaca.alpacajavatraininglast2021.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.entity.AnalyzedReceipt;
import vn.alpaca.alpacajavatraininglast2021.repository.AnalyzedReceiptRepository;

import java.util.Collection;

@Service
public class AnalyzedReceiptService {

    private final AnalyzedReceiptRepository analyzedReceiptRepository;

    public AnalyzedReceiptService(
            AnalyzedReceiptRepository analyzedReceiptRepository) {
        this.analyzedReceiptRepository = analyzedReceiptRepository;
    }

    public Collection<AnalyzedReceipt> findAllReceipts() {
        return analyzedReceiptRepository.findAll();
    }

    public Page<AnalyzedReceipt> findAllReceipts(Pageable pageable) {
        return analyzedReceiptRepository.findAll(pageable);
    }

    public Collection<AnalyzedReceipt> findValidReceipts() {
        return analyzedReceiptRepository.findAllByValidIsTrue();
    }

    public Page<AnalyzedReceipt> findValidReceipts(Pageable pageable) {
        return analyzedReceiptRepository.findAllByValidIsTrue(pageable);
    }

    public Collection<AnalyzedReceipt> findReceiptsByUserId(int userId) {
        return analyzedReceiptRepository.findAllByAnalyzerId(userId);
    }

    public Page<AnalyzedReceipt>
    findReceiptsByUserId(int userId, Pageable pageable) {
        return analyzedReceiptRepository.findAllByAnalyzerId(userId, pageable);
    }

    public Collection<AnalyzedReceipt> findReceiptsByTitle(String title) {
        return analyzedReceiptRepository.findAllByTitle(title);
    }

    public Page<AnalyzedReceipt>
    findReceiptsByTitle(String title, Pageable pageable) {
        return analyzedReceiptRepository.findAllByTitle(title, pageable);
    }

    public AnalyzedReceipt findReceiptById(int id) {
        return analyzedReceiptRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public AnalyzedReceipt findReceiptByClaimRequestId(int requestId) {
        return analyzedReceiptRepository.findByClaimRequestId(requestId)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public AnalyzedReceipt saveReceipt(
            AnalyzedReceipt receipt) {
        return analyzedReceiptRepository.save(receipt);
    }

    public AnalyzedReceipt
    saveInChargeReceipt(int userId, AnalyzedReceipt receipt) {
        if (receipt.getAnalyzer().getId() != userId) {
            throw new AccessDeniedException(); // TODO: implement exception message
        }

        return analyzedReceiptRepository.save(receipt);
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
