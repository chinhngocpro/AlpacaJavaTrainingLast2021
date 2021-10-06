package vn.alpaca.alpacajavatraininglast2021.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.ClaimRequestDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.ClaimRequest;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.ClaimRequestMapper;
import vn.alpaca.alpacajavatraininglast2021.service.ClaimRequestService;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;
import vn.alpaca.alpacajavatraininglast2021.wrapper.request.claimrequest.ClaimRequestForm;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.SuccessResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping(
        value = "/api/v1/claim-requests",
        consumes = "application/json",
        produces = "application/json"
)
public class ClaimRequestController {

    private final ClaimRequestService service;
    private final ClaimRequestMapper mapper;
    private final NullAwareBeanUtil notNullUtil;

    public ClaimRequestController(ClaimRequestService service,
                                  ClaimRequestMapper mapper,
                                  NullAwareBeanUtil notNullUtil) {
        this.service = service;
        this.mapper = mapper;
        this.notNullUtil = notNullUtil;
    }

    @PreAuthorize("hasAuthority('CLAIM_REQUEST_READ')")
    @GetMapping
    public SuccessResponse<Page<ClaimRequestDTO>> getAllClaimRequest(
            @RequestParam("page") Optional<Integer> pageNumber,
            @RequestParam("size") Optional<Integer> pageSize
    ) {

        Pageable pageable = Pageable.unpaged();

        if (pageNumber.isPresent()) {
            pageable = PageRequest.of(pageNumber.get(), pageSize.orElse(5));
        }

        Page<ClaimRequestDTO> dtoPage = new PageImpl<>(
                service.findAllRequests(pageable)
                        .map(mapper::covertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

//    @PreAuthorize("hasAuthority('CLAIM_REQUEST_READ')")
    @GetMapping(value = "/{requestId}")
    public SuccessResponse<ClaimRequestDTO> getClaimRequestById(
            @PathVariable("requestId") int id
    ) {

        ClaimRequestDTO dto =
                mapper.covertToDTO(service.findRequestById(id));

        return new SuccessResponse<>(dto);
    }

//    @PreAuthorize("hasAuthority('CLAIM_REQUEST_CREATE')")
    @PostMapping
    public SuccessResponse<ClaimRequestDTO> createNewClaimRequest(
            @RequestBody ClaimRequestForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        ClaimRequest request = new ClaimRequest();
        notNullUtil.copyProperties(request, formData);

        ClaimRequestDTO dto =
                mapper.covertToDTO(service.saveRequest(request));

        return new SuccessResponse<>(dto);
    }

//    @PreAuthorize("hasAuthority('CLAIM_REQUEST_UPDATE')")
    @PutMapping(value = "/{requestId}")
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

//    @PreAuthorize("hasAuthority('CLAIM_REQUEST_UPDATE')")
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
