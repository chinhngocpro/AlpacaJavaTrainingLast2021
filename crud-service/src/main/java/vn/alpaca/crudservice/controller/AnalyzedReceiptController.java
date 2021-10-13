package vn.alpaca.crudservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.crudservice.object.entity.AnalyzedReceipt;
import vn.alpaca.crudservice.object.entity.ClaimRequest;
import vn.alpaca.crudservice.object.mapper.AnalyzerReceiptMapper;
import vn.alpaca.crudservice.object.wrapper.dto.AnalyzedReceiptDTO;
import vn.alpaca.crudservice.object.wrapper.request.analyzedreceipt.AnalyzedReceiptFilter;
import vn.alpaca.crudservice.object.wrapper.request.analyzedreceipt.AnalyzedReceiptForm;
import vn.alpaca.crudservice.service.AnalyzedReceiptService;
import vn.alpaca.crudservice.service.ClaimRequestService;
import vn.alpaca.response.wrapper.SuccessResponse;
import vn.alpaca.util.ExtractParam;
import vn.alpaca.util.NullAware;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/analyzed-receipts")
public class AnalyzedReceiptController {

    private final AnalyzedReceiptService receiptService;
    private final ClaimRequestService requestService;
    private final AnalyzerReceiptMapper mapper;

    public AnalyzedReceiptController(
            AnalyzedReceiptService receiptService,
            ClaimRequestService requestService,
            AnalyzerReceiptMapper mapper
    ) {
        this.receiptService = receiptService;
        this.requestService = requestService;
        this.mapper = mapper;
    }

    @GetMapping
    public SuccessResponse<Page<AnalyzedReceiptDTO>> getAllReceipts(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody AnalyzedReceiptFilter filter
    ) {
        Sort sort = ExtractParam.getSort(sortBy);
        Pageable pageable =
                ExtractParam.getPageable(pageNumber, pageSize, sort);

        Page<AnalyzedReceiptDTO> dtoPage = new PageImpl<>(
                receiptService.findAllReceipts(filter, pageable)
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @GetMapping(value = "/{receiptId}")
    public SuccessResponse<AnalyzedReceiptDTO> getReceiptById(
            @PathVariable("receiptId") int id
    ) {
        AnalyzedReceiptDTO dto =
                mapper.convertToDTO(receiptService.findReceiptById(id));

        return new SuccessResponse<>(dto);
    }

    @PostMapping
    public SuccessResponse<AnalyzedReceiptDTO> createNewReceipt(
            @RequestBody AnalyzedReceiptForm formData
    ) {
        AnalyzedReceipt receipt = mapper.convertToEntity(formData);

        ClaimRequest request = requestService
                .findRequestById(formData.getClaimRequestId());
        receipt.setClaimRequest(request);

        AnalyzedReceiptDTO dto =
                mapper.convertToDTO(receiptService.saveReceipt(receipt));

        return new SuccessResponse<>(dto);
    }

    @PutMapping(value = "/{receiptId}")
    public SuccessResponse<AnalyzedReceiptDTO> updateReceipt(
            @PathVariable("receiptId") int id,
            @RequestBody AnalyzedReceiptForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        AnalyzedReceipt receipt = receiptService.findReceiptById(id);
        NullAware.getInstance().copyProperties(receipt, formData);

        if (formData.getClaimRequestId() != null) {
            ClaimRequest request = requestService
                    .findRequestById(formData.getClaimRequestId());
            receipt.setClaimRequest(request);
        }

        AnalyzedReceiptDTO dto =
                mapper.convertToDTO(receiptService.saveReceipt(receipt));

        return new SuccessResponse<>(dto);
    }

    @PatchMapping(value = "/{receiptId}/validate")
    public SuccessResponse<Boolean> validateReceipt(
            @PathVariable("receiptId") int id
    ) {
        receiptService.validateReceipt(id);

        return new SuccessResponse<>(true);
    }

    @PatchMapping(value = "/{receiptId}/invalidate")
    public SuccessResponse<Boolean> invalidateReceipt(
            @PathVariable("receiptId") int id
    ) {
        receiptService.invalidateReceipt(id);

        return new SuccessResponse<>(true);
    }
}
