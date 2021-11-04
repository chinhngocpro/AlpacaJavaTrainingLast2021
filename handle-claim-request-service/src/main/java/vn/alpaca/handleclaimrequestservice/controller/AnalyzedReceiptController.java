package vn.alpaca.handleclaimrequestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.common.dto.request.AnalyzedReceiptFilter;
import vn.alpaca.common.dto.request.AnalyzedReceiptRequest;
import vn.alpaca.common.dto.response.AnalyzedReceiptResponse;
import vn.alpaca.common.dto.wrapper.AbstractResponse;
import vn.alpaca.common.dto.wrapper.SuccessResponse;
import vn.alpaca.handleclaimrequestservice.entity.AnalyzedReceipt;
import vn.alpaca.handleclaimrequestservice.mapper.AnalyzedReceiptMapper;
import vn.alpaca.handleclaimrequestservice.service.AnalyzedReceiptService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/analyzed-receipts")
@RequiredArgsConstructor
public class AnalyzedReceiptController {

    private final AnalyzedReceiptService service;
    private final AnalyzedReceiptMapper mapper;

//    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_READ')")
    @GetMapping
    AbstractResponse getAllReceipts(@RequestBody Optional<AnalyzedReceiptFilter> filter) {
        Page<AnalyzedReceipt> analyzedReceipts = service.findAllReceipts(
                filter.orElse(new AnalyzedReceiptFilter()));
        Page<AnalyzedReceiptResponse> response = analyzedReceipts.map(mapper::convertToResponseModel);

        return new SuccessResponse<>(response);
    }

//    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_READ')")
    @GetMapping(value = "/{receiptId}")
    AbstractResponse getReceiptById(@PathVariable("receiptId") int id) {
        AnalyzedReceipt receipt = service.findReceiptById(id);
        AnalyzedReceiptResponse response = mapper.convertToResponseModel(receipt);

        return new SuccessResponse<>(response);
    }

//    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_CREATE')")
    @PostMapping(consumes = "application/json")
    AbstractResponse createNewReceipt(@RequestBody @Valid AnalyzedReceiptRequest requestData) {
        AnalyzedReceipt analyzedReceipt = service.createReceipt(requestData);
        AnalyzedReceiptResponse response = mapper.convertToResponseModel(analyzedReceipt);

        return new SuccessResponse<>(response);
    }

//    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_UPDATE')")
    @PutMapping(value = "/{receiptId}", consumes = "application/json")
    AbstractResponse updateReceipt(@PathVariable("receiptId") int id,
                                   @RequestBody @Valid AnalyzedReceiptRequest requestData) {
        AnalyzedReceipt analyzedReceipt = service.updateReceipt(id, requestData);
        AnalyzedReceiptResponse response = mapper.convertToResponseModel(analyzedReceipt);

        return new SuccessResponse<>(response);
    }

//    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_UPDATE')")
    @PatchMapping(value = "/{receiptId}/validate")
    AbstractResponse validateReceipt(@PathVariable("receiptId") int id) {
        service.validateReceipt(id);

        return new SuccessResponse<>(true);
    }

//    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_UPDATE')")
    @PatchMapping(value = "/{receiptId}/invalidate")
    AbstractResponse invalidateReceipt(@PathVariable("receiptId") int id) {
        service.invalidateReceipt(id);

        return new SuccessResponse<>(true);
    }
}
