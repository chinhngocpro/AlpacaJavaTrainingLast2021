package vn.alpaca.assetsservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.assetsservice.object.mapper.ClaimRequestMapper;
import vn.alpaca.assetsservice.object.wrapper.dto.ClaimRequestDTO;
import vn.alpaca.assetsservice.object.wrapper.request.claimrequest.ClaimRequestFilter;
import vn.alpaca.assetsservice.service.ClaimRequestService;
import vn.alpaca.response.wrapper.SuccessResponse;
import vn.alpaca.util.ExtractParam;

import java.util.Optional;

@RestController
@RequestMapping(value = "/assets/claim-requests")
public class ClaimRequestController {

    private final ClaimRequestService service;
    private final ClaimRequestMapper mapper;

    public ClaimRequestController(ClaimRequestService service,
                                  ClaimRequestMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public SuccessResponse<Page<ClaimRequestDTO>> getAllClaimRequest(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody Optional<ClaimRequestFilter> filter
    ) {
        Sort sort = ExtractParam.getSort(sortBy);
        Pageable pageable =
                ExtractParam.getPageable(pageNumber, pageSize, sort);

        Page<ClaimRequestDTO> dtoPage = new PageImpl<>(
                service.findAllRequests(
                                filter.orElse(new ClaimRequestFilter()),
                                pageable
                        )
                        .map(mapper::covertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @GetMapping(value = "/{requestId}")
    public SuccessResponse<ClaimRequestDTO> getClaimRequestById(
            @PathVariable("requestId") int id
    ) {
        ClaimRequestDTO dto =
                mapper.covertToDTO(service.findRequestById(id));

        return new SuccessResponse<>(dto);
    }

    @PatchMapping(value = "/{requestId}/close")
    public SuccessResponse<Boolean> closeClaimRequest(
            @PathVariable("requestId") int id
    ) {
        service.closeRequest(id);

        return new SuccessResponse<>(true);
    }

    @PatchMapping(value = "/{requestId}/process")
    public SuccessResponse<Boolean> processClaimRequest(
            @PathVariable("requestId") int id
    ) {
        service.processRequest(id);

        return new SuccessResponse<>(true);
    }

    @PatchMapping(value = "/{requestId}/reopen")
    public SuccessResponse<Boolean> reopenClaimRequest(
            @PathVariable("requestId") int id
    ) {
        service.reopenRequest(id);

        return new SuccessResponse<>(true);
    }
}
