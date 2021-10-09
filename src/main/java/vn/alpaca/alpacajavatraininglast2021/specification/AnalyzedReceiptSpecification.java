package vn.alpaca.alpacajavatraininglast2021.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import vn.alpaca.alpacajavatraininglast2021.object.entity.AnalyzedReceipt;
import vn.alpaca.alpacajavatraininglast2021.object.entity.AnalyzedReceipt_;
import vn.alpaca.alpacajavatraininglast2021.object.request.analyzedreceipt.AnalyzedReceiptFilter;

public final class AnalyzedReceiptSpecification {

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
