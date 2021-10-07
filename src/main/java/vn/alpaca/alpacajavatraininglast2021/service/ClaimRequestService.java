package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.entity.ClaimRequest;
import vn.alpaca.alpacajavatraininglast2021.repository.ClaimRequestRepository;
import vn.alpaca.alpacajavatraininglast2021.specification.ClaimRequestSpecification;

@Service
public class ClaimRequestService {

    private final ClaimRequestRepository repository;
    private final ClaimRequestSpecification spec;

    public ClaimRequestService(ClaimRequestRepository repository,
                               ClaimRequestSpecification spec) {
        this.repository = repository;
        this.spec = spec;
    }

    public Page<ClaimRequest> findAllRequests(
            String title,
            String description,
            String status,
            Pageable pageable
    ) {
        Specification<ClaimRequest> conditions = Specification
                .where(spec.hasTitleContaining(title))
                .and(spec.hasDescriptionContaining(description))
                .and(spec.hasStatus(status));

        return repository.findAll(conditions, pageable);
    }

    public ClaimRequest findRequestById(int id) {
        return repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public Page<ClaimRequest>
    findRequestsByCustomerIdCardNumber(
            String idCardNumber,
            String title,
            String description,
            String status,
            Pageable pageable
    ) {
        Specification<ClaimRequest> conditions = Specification
                .where(spec.hasTitleContaining(title))
                .and(spec.hasDescriptionContaining(description))
                .and(spec.hasStatus(status));

        return repository.findAllByCustomerIdCardNumber(
                idCardNumber,
                conditions,
                pageable
        );
    }


    public ClaimRequest saveRequest(ClaimRequest claimRequest) {
        return repository.save(claimRequest);
    }

    public void closeRequest(int requestId) {
        ClaimRequest claimRequest = findRequestById(requestId);
        claimRequest.setStatus("CLOSED");
        saveRequest(claimRequest);
    }

    public void processRequest(int requestId) {
        ClaimRequest claimRequest = findRequestById(requestId);
        claimRequest.setStatus("PROCESSING");
        saveRequest(claimRequest);
    }

    public void reopenRequest(int requestId) {
        ClaimRequest claimRequest = findRequestById(requestId);
        claimRequest.setStatus("PENDING");
        saveRequest(claimRequest);
    }
}
