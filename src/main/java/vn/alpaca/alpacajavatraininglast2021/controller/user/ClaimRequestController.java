package vn.alpaca.alpacajavatraininglast2021.controller.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.ClaimRequestDTO;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.ClaimRequestMapper;
import vn.alpaca.alpacajavatraininglast2021.object.request.claimrequest.ClaimRequestFilter;
import vn.alpaca.alpacajavatraininglast2021.object.response.SuccessResponse;
import vn.alpaca.alpacajavatraininglast2021.service.ClaimRequestService;

import java.util.Optional;

import static vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil.getPageable;
import static vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil.getSort;

@RestController
@RequestMapping(
        value = "/api/user/claim-requests",
        produces = "application/json"
)
public class ClaimRequestController {

    private final ClaimRequestService service;
    private final ClaimRequestMapper mapper;

    public ClaimRequestController(ClaimRequestService service,
                                  ClaimRequestMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PreAuthorize("hasAuthority('CLAIM_REQUEST_READ')")
    @GetMapping
    public SuccessResponse<Page<ClaimRequestDTO>> getAllClaimRequest(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody ClaimRequestFilter filter
    ) {
        Sort sort = getSort(sortBy);
        Pageable pageable = getPageable(pageNumber, pageSize, sort);

        Page<ClaimRequestDTO> dtoPage = new PageImpl<>(
                service.findAllRequests(filter, pageable)
                        .map(mapper::covertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @PreAuthorize("hasAuthority('CLAIM_REQUEST_READ')")
    @GetMapping(value = "/{requestId}")
    public SuccessResponse<ClaimRequestDTO> getClaimRequestById(
            @PathVariable("requestId") int id
    ) {
        ClaimRequestDTO dto =
                mapper.covertToDTO(service.findRequestById(id));

        return new SuccessResponse<>(dto);
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
