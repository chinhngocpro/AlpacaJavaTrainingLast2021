package vn.alpaca.alpacajavatraininglast2021.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.ClaimRequestDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.ClaimRequest;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.ClaimRequestMapper;
import vn.alpaca.alpacajavatraininglast2021.service.ClaimRequestService;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;
import vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil;
import vn.alpaca.alpacajavatraininglast2021.wrapper.request.claimrequest.ClaimRequestForm;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.SuccessResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping(
        value = "/api/v1/claim-requests",
        produces = "application/json"
)
public class ClaimRequestController {

    private final ClaimRequestService service;
    private final ClaimRequestMapper mapper;
    private final NullAwareBeanUtil notNullUtil;
    private final RequestParamUtil paramUtil;

    public ClaimRequestController(ClaimRequestService service,
                                  ClaimRequestMapper mapper,
                                  NullAwareBeanUtil notNullUtil,
                                  RequestParamUtil paramUtil) {
        this.service = service;
        this.mapper = mapper;
        this.notNullUtil = notNullUtil;
        this.paramUtil = paramUtil;
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
            @RequestParam(value = "title", required = false)
                    Optional<String> title,
            @RequestParam(value = "description", required = false)
                    Optional<String> description,
            @RequestParam(value = "status", required = false)
                    Optional<String> status
    ) {

        Sort sort = paramUtil.getSort(sortBy);

        Pageable pageable = paramUtil.getPageable(pageNumber, pageSize, sort);

        Page<ClaimRequestDTO> dtoPage = new PageImpl<>(
                service.findAllRequests(
                                title.orElse(null),
                                description.orElse(null),
                                status.orElse(null),
                                pageable
                        )
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

    @PreAuthorize("hasAuthority('CLAIM_REQUEST_CREATE')")
    @PostMapping(consumes = "application/json")
    public SuccessResponse<ClaimRequestDTO> createNewClaimRequest(
            @RequestBody ClaimRequestForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        ClaimRequest request = new ClaimRequest();
        notNullUtil.copyProperties(request, formData);

        ClaimRequestDTO dto =
                mapper.covertToDTO(service.saveRequest(request));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('CLAIM_REQUEST_UPDATE')")
    @PutMapping(
            value = "/{requestId}",
            consumes = "application/json"
    )
    public SuccessResponse<ClaimRequestDTO> updateClaimRequest(
            @PathVariable("requestId") int id,
            @RequestBody ClaimRequestForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        ClaimRequest request = service.findRequestById(id);
        notNullUtil.copyProperties(request, formData);

        ClaimRequestDTO dto =
                mapper.covertToDTO(service.saveRequest(request));

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
