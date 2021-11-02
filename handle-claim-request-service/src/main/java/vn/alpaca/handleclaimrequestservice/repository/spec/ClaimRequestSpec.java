package vn.alpaca.handleclaimrequestservice.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import vn.alpaca.common.dto.request.ClaimRequestFilter;
import vn.alpaca.handleclaimrequestservice.entity.ClaimRequest;

public final class ClaimRequestSpec {

    public static Specification<ClaimRequest> getSpecification(ClaimRequestFilter filter) {
        return Specification
                .where(hasTitleContaining(filter.getTitle()))
                .and(hasDescriptionContaining(filter.getDescription()))
                .and(hasStatus(filter.getStatus()));
    }

    private static Specification<ClaimRequest> hasTitleContaining(String title) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(title) ?
                builder.conjunction() :
                builder.like(root.get("title"), "%" + title + "%");
    }

    private static Specification<ClaimRequest>
    hasDescriptionContaining(String description) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(description) ?
                builder.conjunction() :
                builder.like(root.get("description"), "%" + description + "%");
    }

    private static Specification<ClaimRequest> hasStatus(String status) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(status) ?
                builder.conjunction() :
                status.equals("PENDING") || status.equals("PROCESSING") || status.equals("CLOSED")
                ? builder.equal(root.get("status"), status)
                : builder.disjunction();

    }
}
