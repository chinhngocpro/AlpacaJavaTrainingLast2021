package vn.alpaca.alpacajavatraininglast2021.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.constant.Authorities;
import vn.alpaca.alpacajavatraininglast2021.object.dto.AnalyzedReceiptDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.AnalyzedReceipt;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.AnalyzerReceiptMapper;
import vn.alpaca.alpacajavatraininglast2021.service.AnalyzedReceiptService;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.SuccessResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/analyzed-receipts")
public class AnalyzedReceiptController {

    private final AnalyzedReceiptService service;
    private final AnalyzerReceiptMapper mapper;
    private final NullAwareBeanUtil notNullUtil;

    public AnalyzedReceiptController(AnalyzedReceiptService service,
                                     AnalyzerReceiptMapper mapper,
                                     NullAwareBeanUtil notNullUtil) {
        this.service = service;
        this.mapper = mapper;
        this.notNullUtil = notNullUtil;
    }

    @GetMapping(
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<Page<AnalyzedReceiptDTO>> getAllReceipts(
            @RequestParam("page") Optional<Integer> pageNumber,
            @RequestParam("size") Optional<Integer> pageSize
    ) {
        Pageable pageable = Pageable.unpaged();

        if (pageNumber.isPresent()) {
            pageable = PageRequest.of(pageNumber.get(), pageSize.orElse(5));
        }

        Page<AnalyzedReceiptDTO> dtoPage = new PageImpl<>(
                service.findAllReceipts(pageable)
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @GetMapping(value = "/{receiptId}",
            consumes = "application/json",
            produces = "application/json")
    public SuccessResponse<AnalyzedReceiptDTO> getReceiptById(
            @PathVariable("receiptId") int id
    ) {
        AnalyzedReceiptDTO dto =
                mapper.convertToDTO(service.findReceiptById(id));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_CREATE')")
    @PostMapping(
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<AnalyzedReceiptDTO> createNewReceipt(
            @RequestBody AnalyzedReceiptDTO receiptDTO
    ) throws InvocationTargetException, IllegalAccessException {
        AnalyzedReceipt receipt = new AnalyzedReceipt();
        notNullUtil.copyProperties(receipt, receiptDTO);

        AnalyzedReceiptDTO dto =
                mapper.convertToDTO(service.saveReceipt(receipt));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_UPDATE')")
    @PutMapping(value = "/{receiptId}",
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<AnalyzedReceiptDTO> updateReceipt(
            @PathVariable("receiptId") int id,
            @RequestBody AnalyzedReceiptDTO receiptDTO
    ) throws InvocationTargetException, IllegalAccessException {
        AnalyzedReceipt receipt = service.findReceiptById(id);
        notNullUtil.copyProperties(receipt, receiptDTO);

        AnalyzedReceiptDTO dto =
                mapper.convertToDTO(service.saveReceipt(receipt));

        return new SuccessResponse<>(dto);
    }

    @PatchMapping(value = "/{receiptId}/validate",
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<Boolean> validateReceipt(
            @PathVariable("receiptId") int id
    ) {
        service.validateReceipt(id);

        return new SuccessResponse<>(true);
    }

    @PatchMapping(value = "/{receiptId}/invalidate",
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<Boolean> invalidateReceipt(
            @PathVariable("receiptId") int id
    ) {
        service.invalidateReceipt(id);

        return new SuccessResponse<>(true);
    }
}
