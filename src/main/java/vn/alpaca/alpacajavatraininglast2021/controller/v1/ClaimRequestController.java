package vn.alpaca.alpacajavatraininglast2021.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/v1/claim-requests")
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

    @GetMapping(
            consumes = "application/json",
            produces = "application/json"
    )
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

    @GetMapping(value = "/{requestId}",
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<ClaimRequestDTO> getClaimRequestById(
            @PathVariable("requestId") int id
    ) {

        ClaimRequestDTO dto =
                mapper.covertToDTO(service.findRequestById(id));

        return new SuccessResponse<>(dto);
    }

    @PostMapping(
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<ClaimRequestDTO> createNewClaimRequest(
            @RequestBody ClaimRequestForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        ClaimRequest request = new ClaimRequest();
        notNullUtil.copyProperties(request, formData);

        ClaimRequestDTO dto =
                mapper.covertToDTO(service.saveRequest(request));

        return new SuccessResponse<>(dto);
    }

    @PutMapping(value = "/{requestId}",
            consumes = "application/json",
            produces = "application/json"
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

    @PatchMapping(value = "/{requestId}/close",
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<Boolean> closeClaimRequest(
            @PathVariable("requestId") int id
    ) {
        service.closeRequest(id);

        return new SuccessResponse<>(true);
    }

    @PatchMapping(value = "/{requestId}/process",
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<Boolean> processClaimRequest(
            @PathVariable("requestId") int id
    ) {
        service.processRequest(id);

        return new SuccessResponse<>(true);
    }

    @PatchMapping(value = "/{requestId}/reopen",
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<Boolean> reopenClaimRequest(
            @PathVariable("requestId") int id
    ) {
        service.reopenRequest(id);

        return new SuccessResponse<>(true);
    }
}
