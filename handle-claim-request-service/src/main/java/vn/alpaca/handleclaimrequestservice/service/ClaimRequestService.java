package vn.alpaca.handleclaimrequestservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.handleclaimrequestservice.client.CustomerFeignClient;
import vn.alpaca.handleclaimrequestservice.object.entity.ClaimRequest;
import vn.alpaca.handleclaimrequestservice.object.entity.ClaimRequest_;
import vn.alpaca.handleclaimrequestservice.object.mapper.ClaimRequestMapper;
import vn.alpaca.handleclaimrequestservice.object.wrapper.request.claimrequest.ClaimRequestFilter;
import vn.alpaca.handleclaimrequestservice.object.wrapper.request.claimrequest.CustomerClaimRequestFilter;
import vn.alpaca.handleclaimrequestservice.object.wrapper.response.ClaimRequestResponse;
import vn.alpaca.handleclaimrequestservice.repository.ClaimRequestRepository;
import vn.alpaca.response.exception.ResourceNotFoundException;

import java.util.Optional;

import static vn.alpaca.handleclaimrequestservice.service.ClaimRequestSpecification.getClaimRequestSpecification;


@Service
public class ClaimRequestService {

    private final ClaimRequestRepository repository;
    private final ClaimRequestMapper mapper;

    private final CustomerFeignClient customerClient;

    public ClaimRequestService(
            ClaimRequestRepository repository,
            ClaimRequestMapper mapper,
            CustomerFeignClient customerClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.customerClient = customerClient;
    }

    public Page<ClaimRequestResponse> findAllRequests(
            ClaimRequestFilter filter,
            Pageable pageable
    ) {
        return repository.findAll(
                getClaimRequestSpecification(filter),
                pageable
        ).map(mapper::convertToResponseModel);
    }

    public ClaimRequestResponse findRequestById(int id) {
        ClaimRequest request = preHandleGetObject(repository.findById(id));

        return mapper.convertToResponseModel(request);
    }

    public Page<ClaimRequestResponse>
    findRequestsByCustomerIdCardNumber(
            CustomerClaimRequestFilter filter,
            Pageable pageable
    ) {
        int customerId = customerClient
                .getByIdCardNumber(filter.getIdCardNumber())
                .getData().getId();

        return repository.findAllByCustomerId(
                customerId,
                getClaimRequestSpecification(filter),
                pageable
        ).map(mapper::convertToResponseModel);
    }

    public void closeRequest(int requestId) {
        ClaimRequest claimRequest =
                preHandleGetObject(repository.findById(requestId));
        claimRequest.setStatus("CLOSED");
        repository.save(claimRequest);
    }

    public void processRequest(int requestId) {
        ClaimRequest claimRequest =
                preHandleGetObject(repository.findById(requestId));
        claimRequest.setStatus("PROCESSING");
        repository.save(claimRequest);
    }

    public void reopenRequest(int requestId) {
        ClaimRequest claimRequest =
                preHandleGetObject(repository.findById(requestId));
        claimRequest.setStatus("PENDING");
        repository.save(claimRequest);
    }

    private ClaimRequest
    preHandleGetObject(Optional<ClaimRequest> optional) {
        return optional.orElseThrow(() -> new ResourceNotFoundException(
                "Claim request id not found."
        ));
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

