package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.object.entity.AnalyzedReceipt;
import vn.alpaca.alpacajavatraininglast2021.object.exception.AccessDeniedException;
import vn.alpaca.alpacajavatraininglast2021.object.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.request.analyzedreceipt.AnalyzedReceiptFilter;
import vn.alpaca.alpacajavatraininglast2021.repository.AnalyzedReceiptRepository;

import static vn.alpaca.alpacajavatraininglast2021.specification.AnalyzedReceiptSpecification.*;

@Service
public class AnalyzedReceiptService {

    private final AnalyzedReceiptRepository repository;

    public AnalyzedReceiptService(AnalyzedReceiptRepository repository) {
        this.repository = repository;
    }

    public Page<AnalyzedReceipt> findAllReceipts(
            AnalyzedReceiptFilter filter,
            Pageable pageable
    ) {

        return repository.findAll(
                getAnalyzedReceiptSpecification(filter),
                pageable
        );
    }

    public AnalyzedReceipt findReceiptById(int id) {
        return repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public AnalyzedReceipt saveReceipt(AnalyzedReceipt receipt) {
        return repository.save(receipt);
    }

    public AnalyzedReceipt
    saveInChargeReceipt(int userId, AnalyzedReceipt receipt) {
        if (receipt.getAnalyzer().getId() != userId) {
            throw new AccessDeniedException(); // TODO: implement exception message
        }

        return repository.save(receipt);
    }

    public void validateReceipt(int receiptId) {
        AnalyzedReceipt receipt = findReceiptById(receiptId);
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
