package vn.alpaca.handleclaimrequestservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.handleclaimrequestservice.object.entity.AnalyzedReceipt;
import vn.alpaca.handleclaimrequestservice.object.entity.AnalyzedReceipt_;
import vn.alpaca.handleclaimrequestservice.object.mapper.AnalyzerReceiptMapper;
import vn.alpaca.handleclaimrequestservice.object.wrapper.request.analyzedreceipt.AnalyzedReceiptFilter;
import vn.alpaca.handleclaimrequestservice.object.wrapper.request.analyzedreceipt.AnalyzedReceiptRequest;
import vn.alpaca.handleclaimrequestservice.object.wrapper.response.AnalyzedReceiptResponse;
import vn.alpaca.handleclaimrequestservice.repository.AnalyzedReceiptRepository;
import vn.alpaca.handleclaimrequestservice.repository.ClaimRequestRepository;
import vn.alpaca.response.exception.ResourceNotFoundException;
import vn.alpaca.util.NullAware;

import java.util.Optional;

import static vn.alpaca.handleclaimrequestservice.service.AnalyzedReceiptSpecification.getAnalyzedReceiptSpecification;

@Service
public class AnalyzedReceiptService {

    private final AnalyzedReceiptRepository repository;
    private final AnalyzerReceiptMapper mapper;

    private final ClaimRequestRepository requestRepository;

    public AnalyzedReceiptService(AnalyzedReceiptRepository repository,
                                  AnalyzerReceiptMapper mapper,
                                  ClaimRequestRepository requestRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.requestRepository = requestRepository;
    }

    public Page<AnalyzedReceiptResponse> findAllReceipts(
            AnalyzedReceiptFilter filter,
            Pageable pageable
    ) {

        return repository.findAll(
                getAnalyzedReceiptSpecification(filter),
                pageable
        ).map(mapper::convertToResponseModel);
    }

    public AnalyzedReceiptResponse findReceiptById(int id) {
        AnalyzedReceipt receipt = preHandleGetObject(repository.findById(id));
        return mapper.convertToResponseModel(receipt);
    }

    public AnalyzedReceiptResponse createReceipt(
            AnalyzedReceiptRequest requestData) {
        AnalyzedReceipt receipt = mapper.convertToEntity(requestData);
        receipt.setClaimRequest(
                requestRepository.findById(requestData.getClaimRequestId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Claim request not found"
                        ))
        );
        return mapper.convertToResponseModel(repository.save(receipt));
    }

    public AnalyzedReceiptResponse
    updateReceipt(int id, AnalyzedReceiptRequest requestData) {
        AnalyzedReceipt receipt =
                preHandleGetObject(repository.findById(id));
        try {
            NullAware.getInstance().copyProperties(receipt, requestData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        receipt.setClaimRequest(
                requestRepository.findById(requestData.getClaimRequestId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Claim request not found"
                        ))
        );

        return mapper.convertToResponseModel(repository.save(receipt));
    }

    public AnalyzedReceipt
    updateInChargeReceipt(int userId, AnalyzedReceipt receipt) {
        // TODO: call user service to validate user before save

        return repository.save(receipt);
    }

    public void validateReceipt(int receiptId) {
        AnalyzedReceipt receipt =
                preHandleGetObject(repository.findById(receiptId));
        receipt.setValid(true);
        repository.save(receipt);
    }

    public void invalidateReceipt(int receiptId) {
        AnalyzedReceipt receipt =
                preHandleGetObject(repository.findById(receiptId));
        receipt.setValid(false);
        repository.save(receipt);
    }

    private AnalyzedReceipt preHandleGetObject(
            Optional<AnalyzedReceipt> optional) {
        return optional.orElseThrow(() -> new ResourceNotFoundException(
                "Receipt not found"
        ));
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

