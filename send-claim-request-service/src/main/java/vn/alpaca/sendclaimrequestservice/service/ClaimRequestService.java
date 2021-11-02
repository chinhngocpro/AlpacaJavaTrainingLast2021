package vn.alpaca.sendclaimrequestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.common.dto.request.ClaimRequestForm;
import vn.alpaca.sendclaimrequestservice.client.CustomerFeignClient;
import vn.alpaca.sendclaimrequestservice.entity.ClaimRequest;
import vn.alpaca.sendclaimrequestservice.mapper.ClaimRequestMapper;
import vn.alpaca.sendclaimrequestservice.repository.ClaimRequestRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClaimRequestService {

    private final ClaimRequestRepository repository;
    private final ClaimRequestMapper mapper;

    private final CustomerFeignClient customerClient;

    private final SaveImageService imageService;


    public ClaimRequest sendRequest(ClaimRequestForm formData) {
        int customerId = customerClient
                .getByIdCardNumber(formData.getIdCardNumber())
                .getData().getId();

        ClaimRequest request = mapper.createClaimRequest(formData);

        if (!ObjectUtils.isEmpty(formData.getReceiptPhotoFiles())) {
            request.setReceiptPhotos(formData.getReceiptPhotoFiles().stream()
                                             .map(imageService::saveFile)
                                             .filter(s -> !ObjectUtils.isEmpty(s))
                                             .collect(Collectors.toList()));
        }

        request.setCustomerId(customerId);

        return repository.save(request);
    }

}
