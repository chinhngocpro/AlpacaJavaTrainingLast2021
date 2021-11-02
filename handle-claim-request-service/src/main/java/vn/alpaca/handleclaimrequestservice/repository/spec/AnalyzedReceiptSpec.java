package vn.alpaca.handleclaimrequestservice.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import vn.alpaca.common.dto.request.AnalyzedReceiptFilter;
import vn.alpaca.handleclaimrequestservice.entity.AnalyzedReceipt;

public final class AnalyzedReceiptSpec {
    public static Specification<AnalyzedReceipt> getSpecification(
            AnalyzedReceiptFilter filter) {
        return Specification.where(isValid(filter.getIsValid()))
                            .and(hasTitleContaining(filter.getTitle()))
                            .and(hasHospitalId(filter.getHospitalId())
                                         .and(hasAccidentId(filter.getAccidentId()))
                                         .and(hasAmountBetween(filter.getMinAmount(),
                                                               filter.getMaxAmount())));
    }

    private static Specification<AnalyzedReceipt> isValid(Boolean isValid) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(isValid)
                ? builder.conjunction()
                : builder.equal(root.get("isValid"), isValid);
    }

    private static Specification<AnalyzedReceipt> hasTitleContaining(String title) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(title)
                ? builder.conjunction()
                : builder.like(root.get("title"), "%" + title + "%");
    }

    private static Specification<AnalyzedReceipt> hasAmountBetween(Double min, Double max) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(min) && ObjectUtils.isEmpty(max)
                ? builder.conjunction()
                : ObjectUtils.isEmpty(min)
                  ? builder.lessThanOrEqualTo(root.get("amount"), max)
                  : ObjectUtils.isEmpty(max)
                    ? builder.greaterThanOrEqualTo(root.get("amount"), min)
                    : builder.between(root.get("amount"), min, max);
    }

    private static Specification<AnalyzedReceipt> hasHospitalId(Integer hospitalId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(hospitalId)
                ? builder.conjunction()
                : builder.equal(root.get("hospitalId"), hospitalId);
    }

    private static Specification<AnalyzedReceipt> hasAccidentId(Integer accidentId) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(accidentId)
                ? builder.conjunction()
                : builder.equal(root.get("accidentId"), accidentId);
    }
}
