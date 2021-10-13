package vn.alpaca.crudservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.crudservice.object.entity.AnalyzedReceipt;
import vn.alpaca.crudservice.object.entity.AnalyzedReceipt_;
import vn.alpaca.crudservice.object.wrapper.request.analyzedreceipt.AnalyzedReceiptFilter;
import vn.alpaca.crudservice.repository.AnalyzedReceiptRepository;
import vn.alpaca.response.exception.ResourceNotFoundException;

import static vn.alpaca.crudservice.service.AnalyzedReceiptSpecification.getAnalyzedReceiptSpecification;

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

final class AnalyzedReceiptSpecification {
    public static Specification<AnalyzedReceipt> getAnalyzedReceiptSpecification(
            AnalyzedReceiptFilter filter) {
        return Specification
                .where(isValid(filter.getIsValid()))
                .and(hasTitleContaining(filter.getTitle()))
                .and(hasHospitalId(filter.getHospitalId())
                        .and(hasAccidentId(filter.getAccidentId()))
                        .and(hasAmountBetween(
                                        filter.getMinAmount(),
                                        filter.getMaxAmount()
                                )
                        ));
    }

    private static Specification<AnalyzedReceipt> isValid(Boolean isValid) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(isValid) ?
                        builder.conjunction() :
                        builder.equal(
                                root.get(AnalyzedReceipt_.IS_VALID),
                                isValid
                        );
    }

    private static Specification<AnalyzedReceipt>
    hasTitleContaining(String title) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(title) ?
                        builder.conjunction() :
                        builder.like(
                                root.get(AnalyzedReceipt_.TITLE),
                                "%" + title + "%"
                        );
    }

    private static Specification<AnalyzedReceipt>
    hasAmountBetween(Double min, Double max) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(min) && ObjectUtils.isEmpty(max) ?
                        builder.conjunction() :
                        ObjectUtils.isEmpty(min) ?
                                builder.lessThanOrEqualTo(
                                        root.get(AnalyzedReceipt_.AMOUNT),
                                        max
                                ) :
                                ObjectUtils.isEmpty(max) ?
                                        builder.greaterThanOrEqualTo(
                                                root.get(
                                                        AnalyzedReceipt_.AMOUNT),
                                                min
                                        ) :
                                        builder.between(
                                                root.get(
                                                        AnalyzedReceipt_.AMOUNT),
                                                min,
                                                max
                                        );
    }

    private static Specification<AnalyzedReceipt>
    hasHospitalId(Integer hospitalId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(hospitalId) ?
                        builder.conjunction() :
                        builder.equal(
                                root.get(AnalyzedReceipt_.HOSPITAL_ID),
                                hospitalId
                        );
    }

    private static Specification<AnalyzedReceipt>
    hasAccidentId(Integer accidentId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(accidentId) ?
                        builder.conjunction() :
                        builder.equal(
                                root.get(AnalyzedReceipt_.ACCIDENT_ID),
                                accidentId
                        );
    }
}

