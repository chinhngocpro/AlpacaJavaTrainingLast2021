package vn.alpaca.alpacajavatraininglast2021.controller.v1;

import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.AnalyzedReceiptDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.AnalyzedReceipt;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.AnalyzerReceiptMapper;
import vn.alpaca.alpacajavatraininglast2021.service.AnalyzedReceiptService;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;
import vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil;
import vn.alpaca.alpacajavatraininglast2021.wrapper.request.analyzedreceipt.AnalyzedReceiptForm;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.SuccessResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping(
        value = "/api/v1/analyzed-receipts",
        produces = "application/json"
)
public class AnalyzedReceiptController {

    private final AnalyzedReceiptService service;
    private final AnalyzerReceiptMapper mapper;
    private final NullAwareBeanUtil notNullUtil;
    private final RequestParamUtil paramUtil;

    public AnalyzedReceiptController(AnalyzedReceiptService service,
                                     AnalyzerReceiptMapper mapper,
                                     NullAwareBeanUtil notNullUtil,
                                     RequestParamUtil paramUtil) {
        this.service = service;
        this.mapper = mapper;
        this.notNullUtil = notNullUtil;
        this.paramUtil = paramUtil;
    }

    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_READ')")
    @GetMapping
    public SuccessResponse<Page<AnalyzedReceiptDTO>> getAllReceipts(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestParam(value = "valid", required = false)
                    Optional<Boolean> isValid,
            @RequestParam(value = "title", required = false)
                    Optional<String> title,
            @RequestParam(value = "hospital-id", required = false)
                    Optional<Integer> hospitalId,
            @RequestParam(value = "accident-id", required = false)
                    Optional<Integer> accidentId,
            @RequestParam(value = "min-amount", required = false)
                    Optional<Double> minAmount,
            @RequestParam(value = "max-amount", required = false)
                    Optional<Double> maxAmount
    ) {
        Sort sort = paramUtil.getSort(sortBy);
        Pageable pageable = paramUtil.getPageable(pageNumber, pageSize, sort);

        Page<AnalyzedReceiptDTO> dtoPage = new PageImpl<>(
                service.findAllReceipts(
                            isValid.orElse(null),
                            title.orElse(null),
                            hospitalId.orElse(null),
                            accidentId.orElse(null),
                            minAmount.orElse(null),
                            maxAmount.orElse(null),
                            pageable
                        )
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
                mapper.convertToDTO(service.findReceiptById(id));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_CREATE')")
    @PostMapping(consumes = "application/json")
    public SuccessResponse<AnalyzedReceiptDTO> createNewReceipt(
            @RequestBody AnalyzedReceiptForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        AnalyzedReceipt receipt = new AnalyzedReceipt();
        notNullUtil.copyProperties(receipt, formData);

        AnalyzedReceiptDTO dto =
                mapper.convertToDTO(service.saveReceipt(receipt));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_UPDATE')")
    @PutMapping(
            value = "/{receiptId}",
            consumes = "application/json"
    )
    public SuccessResponse<AnalyzedReceiptDTO> updateReceipt(
            @PathVariable("receiptId") int id,
            @RequestBody AnalyzedReceiptForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        AnalyzedReceipt receipt = service.findReceiptById(id);
        notNullUtil.copyProperties(receipt, formData);

        AnalyzedReceiptDTO dto =
                mapper.convertToDTO(service.saveReceipt(receipt));

        return new SuccessResponse<>(dto);
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
