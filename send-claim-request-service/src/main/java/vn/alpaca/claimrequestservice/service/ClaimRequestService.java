package vn.alpaca.claimrequestservice.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.claimrequestservice.object.entity.ClaimRequest;
import vn.alpaca.claimrequestservice.object.entity.Customer;
import vn.alpaca.claimrequestservice.object.mapper.ClaimRequestMapper;
import vn.alpaca.claimrequestservice.object.wrapper.dto.ClaimRequestDTO;
import vn.alpaca.claimrequestservice.object.wrapper.request.ClaimRequestForm;
import vn.alpaca.claimrequestservice.repository.ClaimRequestRepository;
import vn.alpaca.claimrequestservice.repository.CustomerRepository;
import vn.alpaca.response.exception.AccessDeniedException;

import java.util.stream.Collectors;

@Service
public class ClaimRequestService {

    private final ClaimRequestRepository requestRepository;
    private final CustomerRepository customerRepository;
    private final ClaimRequestMapper requestMapper;

    private final SaveImageService imageService;

    public ClaimRequestService(
            ClaimRequestRepository requestRepository,
            CustomerRepository customerRepository,
            ClaimRequestMapper requestMapper,
            SaveImageService imageService
    ) {
        this.requestRepository = requestRepository;
        this.customerRepository = customerRepository;
        this.requestMapper = requestMapper;
        this.imageService = imageService;
    }

    public ClaimRequestDTO
    sendRequest(ClaimRequestForm formData) {
        Customer customer = customerRepository
                .findByIdCardNumber(formData.getIdCardNumber())
                .orElseThrow(() -> new AccessDeniedException(
                        "Customer identity does not exist."
                ));
        if (!customer.isActive()) {
            throw new AccessDeniedException("This customer is disabled.");
        }

        ClaimRequest request = requestMapper.convertToEntity(formData);

        if (!ObjectUtils.isEmpty(formData.getReceiptPhotoFiles())) {
            request.setReceiptPhotos(formData.getReceiptPhotoFiles().stream()
                    .map(imageService::saveFile)
                    .filter(s -> !ObjectUtils.isEmpty(s))
                    .collect(Collectors.toList()));
        }

        request.setCustomer(customer);

        return requestMapper.covertToDTO(
                requestRepository.save(request)
        );
    }

}
