package vn.alpaca.sendclaimrequestservice.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.alpaca.sendclaimrequestservice.object.wrapper.response.ClaimRequestDTO;
import vn.alpaca.sendclaimrequestservice.object.wrapper.request.ClaimRequestForm;
import vn.alpaca.sendclaimrequestservice.service.ClaimRequestService;
import vn.alpaca.response.wrapper.SuccessResponse;

@RestController
@RequestMapping("/send-request")
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
