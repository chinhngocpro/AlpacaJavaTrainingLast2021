package vn.alpaca.handleclaimrequestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.common.dto.request.ClaimRequestFilter;
import vn.alpaca.common.dto.request.CustomerClaimRequestFilter;
import vn.alpaca.common.dto.response.ClaimRequestResponse;
import vn.alpaca.common.dto.wrapper.AbstractResponse;
import vn.alpaca.common.dto.wrapper.SuccessResponse;
import vn.alpaca.handleclaimrequestservice.entity.ClaimRequest;
import vn.alpaca.handleclaimrequestservice.mapper.ClaimRequestMapper;
import vn.alpaca.handleclaimrequestservice.service.ClaimRequestService;

import java.util.Optional;

@RestController
@RequestMapping("/claim-requests")
@RequiredArgsConstructor
public class ClaimRequestController {

    private final ClaimRequestService service;
    private final ClaimRequestMapper mapper;

//    @PreAuthorize("hasAuthority('CLAIM_REQUEST_READ')")
    @GetMapping
    AbstractResponse getAllClaimRequest(@RequestBody Optional<ClaimRequestFilter> filter) {
        System.out.println(filter);
        Page<ClaimRequest> claimRequests = service.findAllRequests(filter.orElse(new ClaimRequestFilter()));
        Page<ClaimRequestResponse> response = claimRequests.map(mapper::convertToResponseModel);

        return new SuccessResponse<>(response);
    }

//    @PreAuthorize("hasAuthority('CLAIM_REQUEST_READ')")
    @GetMapping(value = "/{requestId}")
    AbstractResponse getClaimRequestById(@PathVariable("requestId") int id) {
        ClaimRequest claimRequest = service.findRequestById(id);
        ClaimRequestResponse response = mapper.convertToResponseModel(claimRequest);

        return new SuccessResponse<>(response);
    }

    @GetMapping("/customer")
    AbstractResponse getClaimRequestsByCustomerIdCard(
            @RequestBody CustomerClaimRequestFilter filter) {
        Page<ClaimRequest> claimRequests = service.findRequestsByCustomerIdCardNumber(filter);
        Page<ClaimRequestResponse> response = claimRequests.map(mapper::convertToResponseModel);

        return new SuccessResponse<>(response);
    }

//    @PreAuthorize("hasAuthority('CLAIM_REQUEST_UPDATE')")
    @PatchMapping(value = "/{requestId}/close")
    AbstractResponse closeClaimRequest(@PathVariable("requestId") int id) {
        service.closeRequest(id);

        return new SuccessResponse<>(true);
    }

//    @PreAuthorize("hasAuthority('CLAIM_REQUEST_UPDATE')")
    @PatchMapping(value = "/{requestId}/process")
    AbstractResponse processClaimRequest(@PathVariable("requestId") int id) {
        service.processRequest(id);

        return new SuccessResponse<>(true);
    }

//    @PreAuthorize("hasAuthority('CLAIM_REQUEST_UPDATE')")
    @PatchMapping(value = "/{requestId}/reopen")
    AbstractResponse reopenClaimRequest(@PathVariable("requestId") int id) {
        service.reopenRequest(id);

        return new SuccessResponse<>(true);
    }
}
