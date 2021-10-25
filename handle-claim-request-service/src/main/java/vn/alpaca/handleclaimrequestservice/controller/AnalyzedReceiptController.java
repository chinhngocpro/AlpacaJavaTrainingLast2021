package vn.alpaca.handleclaimrequestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.handleclaimrequestservice.object.wrapper.request.analyzedreceipt.AnalyzedReceiptFilter;
import vn.alpaca.handleclaimrequestservice.object.wrapper.request.analyzedreceipt.AnalyzedReceiptRequest;
import vn.alpaca.handleclaimrequestservice.object.wrapper.response.AnalyzedReceiptResponse;
import vn.alpaca.handleclaimrequestservice.service.AnalyzedReceiptService;
import vn.alpaca.dto.wrapper.SuccessResponse;
import vn.alpaca.util.ExtractParam;

import java.util.Optional;

@RestController
@RequestMapping("/analyzed-receipts")
@RequiredArgsConstructor
public class AnalyzedReceiptController {

    private final AnalyzedReceiptService service;

    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_READ')")
    @GetMapping
    public SuccessResponse<Page<AnalyzedReceiptResponse>> getAllReceipts(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "limit", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody Optional<AnalyzedReceiptFilter> filter
    ) {
        Pageable pageable = ExtractParam.getPageable(
                pageNumber, pageSize,
                ExtractParam.getSort(sortBy)
        );

        Page<AnalyzedReceiptResponse> responseData = service.findAllReceipts(
                filter.orElse(new AnalyzedReceiptFilter()),
                pageable
        );

        return new SuccessResponse<>(responseData);
    }

    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_READ')")
    @GetMapping(value = "/{receiptId}")
    public SuccessResponse<AnalyzedReceiptResponse> getReceiptById(
            @PathVariable("receiptId") int id
    ) {
        AnalyzedReceiptResponse responseData = service.findReceiptById(id);

        return new SuccessResponse<>(responseData);
    }

    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_CREATE')")
    @PostMapping(consumes = "application/json")
    public SuccessResponse<AnalyzedReceiptResponse> createNewReceipt(
            @RequestBody AnalyzedReceiptRequest requestData
    ) {

        AnalyzedReceiptResponse responseData =
                service.createReceipt(requestData);

        return new SuccessResponse<>(responseData);
    }

    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_UPDATE')")
    @PutMapping(
            value = "/{receiptId}",
            consumes = "application/json"
    )
    public SuccessResponse<AnalyzedReceiptResponse> updateReceipt(
            @PathVariable("receiptId") int id,
            @RequestBody AnalyzedReceiptRequest requestData
    ) {

        AnalyzedReceiptResponse responseData =
                service.updateReceipt(id, requestData);

        return new SuccessResponse<>(responseData);
    }

    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_UPDATE')")
    @PatchMapping(value = "/{receiptId}/validate")
    public SuccessResponse<Boolean> validateReceipt(
            @PathVariable("receiptId") int id
    ) {
        service.validateReceipt(id);

        return new SuccessResponse<>(true);
    }

    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_UPDATE')")
    @PatchMapping(value = "/{receiptId}/invalidate")
    public SuccessResponse<Boolean> invalidateReceipt(
            @PathVariable("receiptId") int id
    ) {
        service.invalidateReceipt(id);

        return new SuccessResponse<>(true);
    }
}
