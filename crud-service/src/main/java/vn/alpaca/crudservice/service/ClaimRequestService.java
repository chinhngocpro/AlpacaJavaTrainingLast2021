package vn.alpaca.crudservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.crudservice.object.entity.ClaimRequest;
import vn.alpaca.crudservice.object.entity.ClaimRequest_;
import vn.alpaca.crudservice.object.wrapper.request.claimrequest.ClaimRequestFilter;
import vn.alpaca.crudservice.repository.ClaimRequestRepository;
import vn.alpaca.crudservice.repository.CustomerRepository;
import vn.alpaca.response.exception.AccessDeniedException;
import vn.alpaca.response.exception.ResourceNotFoundException;

import static vn.alpaca.crudservice.service.ClaimRequestSpecification.getClaimRequestSpecification;


@Service
public class ClaimRequestService {

    private final ClaimRequestRepository requestRepository;
    private final CustomerRepository customerRepository;


    public ClaimRequestService(
            ClaimRequestRepository requestRepository,
            CustomerRepository customerRepository) {
        this.requestRepository = requestRepository;
        this.customerRepository = customerRepository;
    }

    public Page<ClaimRequest> findAllRequests(
            ClaimRequestFilter filter,
            Pageable pageable
    ) {
        return requestRepository.findAll(
                getClaimRequestSpecification(filter),
                pageable
        );
    }

    public ClaimRequest findRequestById(int id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Claim request id not found."
                ));
    }

    public Page<ClaimRequest>
    findRequestsByCustomerIdCardNumber(
            String idCardNumber,
            ClaimRequestFilter filter,
            Pageable pageable
    ) {
        customerRepository.findByIdCardNumber(idCardNumber)
                .orElseThrow(() -> new AccessDeniedException(
                        "Customer identity does not exist."
                ));

        return requestRepository.findAllByCustomerIdCardNumber(
                idCardNumber,
                getClaimRequestSpecification(filter),
                pageable
        );
    }

    public void closeRequest(int requestId) {
        ClaimRequest claimRequest = findRequestById(requestId);
        claimRequest.setStatus("CLOSED");
        requestRepository.save(claimRequest);
    }

    public void processRequest(int requestId) {
        ClaimRequest claimRequest = findRequestById(requestId);
        claimRequest.setStatus("PROCESSING");
        requestRepository.save(claimRequest);
    }

    public void reopenRequest(int requestId) {
        ClaimRequest claimRequest = findRequestById(requestId);
        claimRequest.setStatus("PENDING");
        requestRepository.save(claimRequest);
    }
}

final class ClaimRequestSpecification {

    public static Specification<ClaimRequest>
    getClaimRequestSpecification(ClaimRequestFilter filter) {
        return Specification
                .where(hasTitleContaining(filter.getTitle()))
                .and(hasDescriptionContaining(filter.getDescription()))
                .and(hasStatus(filter.getStatus()));
    }

    private static Specification<ClaimRequest>
    hasTitleContaining(String title) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(title) ?
                        builder.conjunction() :
                        builder.like(
                                root.get(ClaimRequest_.TITLE),
                                "%" + title + "%"
                        );
    }

    private static Specification<ClaimRequest>
    hasDescriptionContaining(String description) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(description) ?
                        builder.conjunction() :
                        builder.like(
                                root.get(ClaimRequest_.DESCRIPTION),
                                "%" + description + "%"
                        );
    }

    private static Specification<ClaimRequest>
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

