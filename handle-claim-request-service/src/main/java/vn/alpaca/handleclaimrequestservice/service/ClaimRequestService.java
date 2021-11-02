package vn.alpaca.handleclaimrequestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.alpaca.common.dto.request.ClaimRequestFilter;
import vn.alpaca.common.dto.request.CustomerClaimRequestFilter;
import vn.alpaca.common.exception.ResourceNotFoundException;
import vn.alpaca.handleclaimrequestservice.client.CustomerFeignClient;
import vn.alpaca.handleclaimrequestservice.entity.ClaimRequest;
import vn.alpaca.handleclaimrequestservice.mapper.ClaimRequestMapper;
import vn.alpaca.handleclaimrequestservice.repository.ClaimRequestRepository;
import vn.alpaca.handleclaimrequestservice.repository.spec.ClaimRequestSpec;


@Service
@RequiredArgsConstructor
public class ClaimRequestService {

    private final ClaimRequestRepository repository;
    private final ClaimRequestMapper mapper;

    private final CustomerFeignClient customerClient;

    public Page<ClaimRequest> findAllRequests(ClaimRequestFilter filter) {
        return repository.findAll(ClaimRequestSpec.getSpecification(filter),
                                  filter.getPagination().getPageAndSort());
    }

    public ClaimRequest findRequestById(int id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Claim request id not found."));
    }

    public Page<ClaimRequest>
    findRequestsByCustomerIdCardNumber(CustomerClaimRequestFilter filter) {
        int customerId = customerClient.getByIdCardNumber(filter.getIdCardNumber())
                                       .getData().getId();

        return repository.findAllByCustomerId(customerId,
                                              ClaimRequestSpec.getSpecification(filter),
                                              filter.getPagination().getPageAndSort());
    }

    public void closeRequest(int requestId) {
        ClaimRequest claimRequest = findRequestById(requestId);
        claimRequest.setStatus("CLOSED");
        repository.save(claimRequest);
    }

    public void processRequest(int requestId) {
        ClaimRequest claimRequest = findRequestById(requestId);
        claimRequest.setStatus("PROCESSING");
        repository.save(claimRequest);
    }

    public void reopenRequest(int requestId) {
        ClaimRequest claimRequest = findRequestById(requestId);
        claimRequest.setStatus("PENDING");
        repository.save(claimRequest);
    }

}

