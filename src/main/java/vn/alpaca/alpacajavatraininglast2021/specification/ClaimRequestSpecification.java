package vn.alpaca.alpacajavatraininglast2021.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import vn.alpaca.alpacajavatraininglast2021.object.entity.ClaimRequest;
import vn.alpaca.alpacajavatraininglast2021.object.entity.ClaimRequest_;

@Component
public final class ClaimRequestSpecification {

    public Specification<ClaimRequest>
    hasTitleContaining(String title) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(title) ?
                        builder.conjunction() :
                        builder.like(
                                root.get(ClaimRequest_.TITLE),
                                "%" + title + "%"
                        );
    }

    public Specification<ClaimRequest>
    hasDescriptionContaining(String description) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(description) ?
                        builder.conjunction() :
                        builder.like(
                                root.get(ClaimRequest_.DESCRIPTION),
                                "%" + description + "%"
                        );
    }

    public Specification<ClaimRequest>
    hasStatus(String status) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(status) ?
                        builder.conjunction() :
                        status.equals("PENDING") ||
                                status.equals("PROCESSING") ||
                                status.equals("CLOSED") ?
                                builder.equal(
                                        root.get(ClaimRequest_.STATUS),
                                        status
                                ) :
                                builder.disjunction();

    }
}
