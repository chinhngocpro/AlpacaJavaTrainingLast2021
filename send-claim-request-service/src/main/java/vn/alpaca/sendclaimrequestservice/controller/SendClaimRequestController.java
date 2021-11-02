package vn.alpaca.sendclaimrequestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.alpaca.common.dto.request.ClaimRequestForm;
import vn.alpaca.common.dto.response.ClaimRequestResponse;
import vn.alpaca.common.dto.wrapper.AbstractResponse;
import vn.alpaca.common.dto.wrapper.SuccessResponse;
import vn.alpaca.sendclaimrequestservice.entity.ClaimRequest;
import vn.alpaca.sendclaimrequestservice.mapper.ClaimRequestMapper;
import vn.alpaca.sendclaimrequestservice.service.ClaimRequestService;

import javax.validation.Valid;

@RestController
@RequestMapping("/send-request")
@RequiredArgsConstructor
public class SendClaimRequestController {

    private final ClaimRequestService service;
    private final ClaimRequestMapper mapper;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    AbstractResponse createNewClaimRequest(@ModelAttribute @Valid ClaimRequestForm formData) {
        ClaimRequest claimRequest = service.sendRequest(formData);
        ClaimRequestResponse response = mapper.convertToResponseModel(claimRequest);

        return new SuccessResponse<>(response);
    }
}
