package vn.alpaca.sendclaimrequestservice.service;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.response.exception.AccessDeniedException;
import vn.alpaca.sendclaimrequestservice.client.CustomerFeignClient;
import vn.alpaca.sendclaimrequestservice.object.entity.ClaimRequest;
import vn.alpaca.sendclaimrequestservice.object.mapper.ClaimRequestMapper;
import vn.alpaca.sendclaimrequestservice.object.wrapper.request.ClaimRequestForm;
import vn.alpaca.sendclaimrequestservice.object.wrapper.response.ClaimRequestDTO;
import vn.alpaca.sendclaimrequestservice.repository.ClaimRequestRepository;

import java.util.stream.Collectors;

@Service
public class ClaimRequestService {

    private final ClaimRequestRepository repository;
    private final ClaimRequestMapper mapper;

    private final CustomerFeignClient customerClient;

    private final SaveImageService imageService;

    public ClaimRequestService(
            ClaimRequestRepository repository,
            ClaimRequestMapper mapper,
            CustomerFeignClient customerClient,
            SaveImageService imageService
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.customerClient = customerClient;
        this.imageService = imageService;
    }

    public ClaimRequestDTO
    sendRequest(ClaimRequestForm formData) throws AccessDeniedException {
        int customerId = customerClient
                .getByIdCardNumber(formData.getIdCardNumber())
                .getData().getId();

        ClaimRequest request = mapper.convertToEntity(formData);

        if (!ObjectUtils.isEmpty(formData.getReceiptPhotoFiles())) {
            request.setReceiptPhotos(formData.getReceiptPhotoFiles().stream()
                    .map(imageService::saveFile)
                    .filter(s -> !ObjectUtils.isEmpty(s))
                    .collect(Collectors.toList()));
        }

        request.setCustomerId(customerId);

        return mapper.covertToDTO(
                repository.save(request)
        );
    }

}
