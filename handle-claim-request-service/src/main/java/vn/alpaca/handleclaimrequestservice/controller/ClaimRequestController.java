package vn.alpaca.handleclaimrequestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.handleclaimrequestservice.object.wrapper.request.claimrequest.ClaimRequestFilter;
import vn.alpaca.handleclaimrequestservice.object.wrapper.request.claimrequest.CustomerClaimRequestFilter;
import vn.alpaca.handleclaimrequestservice.object.wrapper.response.ClaimRequestResponse;
import vn.alpaca.handleclaimrequestservice.service.ClaimRequestService;
import vn.alpaca.dto.wrapper.SuccessResponse;
import vn.alpaca.util.ExtractParam;

import java.util.Optional;

@RestController
@RequestMapping("/claim-requests")
@RequiredArgsConstructor
public class ClaimRequestController {

    private final ClaimRequestService service;

    @PreAuthorize("hasAuthority('CLAIM_REQUEST_READ')")
    @GetMapping
    public SuccessResponse<Page<ClaimRequestResponse>> getAllClaimRequest(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "limit", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody Optional<ClaimRequestFilter> filter
    ) {
        Pageable pageable = ExtractParam.getPageable(
                pageNumber, pageSize,
                ExtractParam.getSort(sortBy)
        );

        Page<ClaimRequestResponse> responseData = service.findAllRequests(
                filter.orElse(new ClaimRequestFilter()),
                pageable
        );

        return new SuccessResponse<>(responseData);
    }

    @PreAuthorize("hasAuthority('CLAIM_REQUEST_READ')")
    @GetMapping(value = "/{requestId}")
    public SuccessResponse<ClaimRequestResponse> getClaimRequestById(
            @PathVariable("requestId") int id
    ) {
        ClaimRequestResponse responseData = service.findRequestById(id);

        return new SuccessResponse<>(responseData);
    }

    @GetMapping("/customer")
    public SuccessResponse<Page<ClaimRequestResponse>> getClaimRequestsByCustomerIdCard(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "limit", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody CustomerClaimRequestFilter filter
    ) {
        Pageable pageable = ExtractParam.getPageable(
                pageNumber, pageSize,
                ExtractParam.getSort(sortBy)
        );

        Page<ClaimRequestResponse> responseData =
                service.findRequestsByCustomerIdCardNumber(
                        filter,
                        pageable
                );

        return new SuccessResponse<>(responseData);
    }

    @PreAuthorize("hasAuthority('CLAIM_REQUEST_UPDATE')")
    @PatchMapping(value = "/{requestId}/close")
    public SuccessResponse<Boolean> closeClaimRequest(
            @PathVariable("requestId") int id
    ) {
        service.closeRequest(id);

        return new SuccessResponse<>(true);
    }

    @PreAuthorize("hasAuthority('CLAIM_REQUEST_UPDATE')")
    @PatchMapping(value = "/{requestId}/process")
    public SuccessResponse<Boolean> processClaimRequest(
            @PathVariable("requestId") int id
    ) {
        service.processRequest(id);

        return new SuccessResponse<>(true);
    }

    @PreAuthorize("hasAuthority('CLAIM_REQUEST_UPDATE')")
    @PatchMapping(value = "/{requestId}/reopen")
    public SuccessResponse<Boolean> reopenClaimRequest(
            @PathVariable("requestId") int id
    ) {
        service.reopenRequest(id);

        return new SuccessResponse<>(true);
    }
}
