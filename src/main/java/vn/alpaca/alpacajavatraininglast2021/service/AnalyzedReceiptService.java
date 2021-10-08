package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exception.AccessDeniedException;
import vn.alpaca.alpacajavatraininglast2021.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.entity.AnalyzedReceipt;
import vn.alpaca.alpacajavatraininglast2021.repository.AnalyzedReceiptRepository;
import vn.alpaca.alpacajavatraininglast2021.specification.AnalyzedReceiptSpecification;

@Service
public class AnalyzedReceiptService {

    private final AnalyzedReceiptRepository repository;
    private final AnalyzedReceiptSpecification spec;

    public AnalyzedReceiptService(AnalyzedReceiptRepository repository,
                                  AnalyzedReceiptSpecification spec) {
        this.repository = repository;
        this.spec = spec;
    }

    public Page<AnalyzedReceipt> findAllReceipts(
            Boolean isValid,
            String title,
            Integer hospitalId,
            Integer accidentId,
            Double minAmount,
            Double maxAmount,
            Pageable pageable
    ) {

        Specification<AnalyzedReceipt> conditions = Specification
                .where(spec.isValid(isValid))
                .and(spec.hasTitleContaining(title))
                .and(spec.hasHospitalId(hospitalId))
                .and(spec.hasAcccidentId(accidentId))
                .and(spec.hasAmountBetween(minAmount, maxAmount));

        return repository.findAll(conditions, pageable);
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
