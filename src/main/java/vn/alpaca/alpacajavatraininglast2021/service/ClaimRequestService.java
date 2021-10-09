package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.alpacajavatraininglast2021.object.entity.ClaimRequest;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Customer;
import vn.alpaca.alpacajavatraininglast2021.object.exception.AccessDeniedException;
import vn.alpaca.alpacajavatraininglast2021.object.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.ClaimRequestMapper;
import vn.alpaca.alpacajavatraininglast2021.object.request.claimrequest.ClaimRequestFilter;
import vn.alpaca.alpacajavatraininglast2021.object.request.claimrequest.ClaimRequestForm;
import vn.alpaca.alpacajavatraininglast2021.repository.ClaimRequestRepository;
import vn.alpaca.alpacajavatraininglast2021.repository.CustomerRepository;

import java.util.stream.Collectors;

import static vn.alpaca.alpacajavatraininglast2021.specification.ClaimRequestSpecification.getClaimRequestSpecification;

@Service
public class ClaimRequestService {

    private final ClaimRequestRepository requestRepository;
    private final CustomerRepository customerRepository;
    private final FileService fileService;
    private final ClaimRequestMapper requestMapper;


    public ClaimRequestService(
            ClaimRequestRepository requestRepository,
            CustomerRepository customerRepository,
            FileService fileService,
            ClaimRequestMapper requestMapper
    ) {
        this.requestRepository = requestRepository;
        this.customerRepository = customerRepository;
        this.fileService = fileService;
        this.requestMapper = requestMapper;
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


    public ClaimRequest
    saveRequest(String idCardNumber, ClaimRequestForm formData) {
        Customer customer = customerRepository.findByIdCardNumber(idCardNumber)
                .orElseThrow(() -> new AccessDeniedException(
                        "Customer identity does not exist."
                ));
        if (!customer.isActive()) {
            throw new AccessDeniedException("This customer is disabled.");
        }

        ClaimRequest request = requestMapper.convertToEntity(formData);

        if (!ObjectUtils.isEmpty(formData.getReceiptPhotoFiles())) {
            request.setReceiptPhotos(formData.getReceiptPhotoFiles().stream()
                    .map(fileService::saveFile)
                    .filter(s -> !ObjectUtils.isEmpty(s))
                    .collect(Collectors.toList()));
        }

        request.setCustomer(customer);
        return requestRepository.save(request);
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
