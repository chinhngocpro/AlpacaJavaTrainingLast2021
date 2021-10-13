package vn.alpaca.claimrequestservice.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.alpaca.claimrequestservice.object.wrapper.dto.ClaimRequestDTO;
import vn.alpaca.claimrequestservice.object.wrapper.request.ClaimRequestForm;
import vn.alpaca.claimrequestservice.service.ClaimRequestService;
import vn.alpaca.response.wrapper.SuccessResponse;

@RestController
@RequestMapping("/make-claim-requests") // convention naming?
public class SendClaimRequestController {

    private final ClaimRequestService service;

    public SendClaimRequestController(ClaimRequestService service) {
        this.service = service;
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public SuccessResponse<ClaimRequestDTO> createNewClaimRequest(
            @ModelAttribute("formData") ClaimRequestForm formData
    ) {
        ClaimRequestDTO dto = service.sendRequest(formData);

        return new SuccessResponse<>(dto);
    }
}
