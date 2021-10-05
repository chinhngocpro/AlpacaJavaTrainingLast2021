package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.entity.ClaimRequest;
import vn.alpaca.alpacajavatraininglast2021.repository.ClaimRequestRepository;

import java.util.Collection;

@Service
public class ClaimRequestService {

    private final ClaimRequestRepository requestRepository;

    public ClaimRequestService(ClaimRequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Collection<ClaimRequest> findAllRequests() {
        return requestRepository.findAll();
    }

    public Page<ClaimRequest> findAllRequests(Pageable pageable) {
        return requestRepository.findAll(pageable);
    }

    public Collection<ClaimRequest> findRequestsByTitleContains(String title) {
        return requestRepository.findAllByTitleContains(title);
    }

    public Page<ClaimRequest>
    findRequestsByTitleContains(String title, Pageable pageable) {
        return requestRepository.findAllByTitleContains(title, pageable);
    }

    public Collection<ClaimRequest>
    findRequestsByDescriptionContains(String description) {
        return requestRepository.findAllByDescriptionContains(description);
    }

    public Page<ClaimRequest>
    findRequestsByDescriptionContains(String description, Pageable pageable) {
        return requestRepository
                .findAllByDescriptionContains(description, pageable);
    }

    public Collection<ClaimRequest> findRequestsByCustomerId(int customerId) {
        return requestRepository.findAllByCustomerId(customerId);
    }

    public Page<ClaimRequest>
    findRequestsByCustomerId(int customerId, Pageable pageable) {
        return requestRepository.findAllByCustomerId(customerId, pageable);
    }

    public Collection<ClaimRequest> findRequestsByUserId(int userId) {
        return requestRepository.findAllByEmployeeInChargeId(userId);
    }

    public Page<ClaimRequest>
    findRequestsByUserId(int userId, Pageable pageable) {
        return requestRepository.findAllByEmployeeInChargeId(userId, pageable);
    }

    public Collection<ClaimRequest> findRequestsByStatus(String status) {
        return requestRepository.findAllByStatus(status);
    }

    public Page<ClaimRequest>
    findRequestsByStatus(String status, Pageable pageable) {
        return requestRepository.findAllByStatus(status, pageable);
    }

    public ClaimRequest findRequestById(int id) {
        return requestRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public ClaimRequest saveRequest(ClaimRequest claimRequest) {
        return requestRepository.save(claimRequest);
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
