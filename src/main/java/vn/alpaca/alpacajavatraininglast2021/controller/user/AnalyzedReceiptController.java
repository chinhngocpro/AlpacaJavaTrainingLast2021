package vn.alpaca.alpacajavatraininglast2021.controller.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.AnalyzedReceiptDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.AnalyzedReceipt;
import vn.alpaca.alpacajavatraininglast2021.object.entity.ClaimRequest;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.AnalyzerReceiptMapper;
import vn.alpaca.alpacajavatraininglast2021.object.request.analyzedreceipt.AnalyzedReceiptFilter;
import vn.alpaca.alpacajavatraininglast2021.object.request.analyzedreceipt.AnalyzedReceiptForm;
import vn.alpaca.alpacajavatraininglast2021.object.response.SuccessResponse;
import vn.alpaca.alpacajavatraininglast2021.service.AnalyzedReceiptService;
import vn.alpaca.alpacajavatraininglast2021.service.ClaimRequestService;
import vn.alpaca.alpacajavatraininglast2021.service.UserService;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import static vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil.getPageable;
import static vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil.getSort;

@RestController
@RequestMapping(
        value = "/api/user/analyzed-receipts",
        produces = "application/json"
)
public class AnalyzedReceiptController {

    private final AnalyzedReceiptService receiptService;
    private final ClaimRequestService requestService;
    private final UserService userService;
    private final AnalyzerReceiptMapper mapper;

    public AnalyzedReceiptController(
            AnalyzedReceiptService receiptService,
            ClaimRequestService requestService,
            UserService userService,
            AnalyzerReceiptMapper mapper
    ) {
        this.receiptService = receiptService;
        this.requestService = requestService;
        this.userService = userService;
        this.mapper = mapper;
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
            @RequestBody AnalyzedReceiptFilter filter
    ) {
        Sort sort = getSort(sortBy);
        Pageable pageable = getPageable(pageNumber, pageSize, sort);

        Page<AnalyzedReceiptDTO> dtoPage = new PageImpl<>(
                receiptService.findAllReceipts(filter, pageable)
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_READ')")
    @GetMapping(value = "/{receiptId}")
    public SuccessResponse<AnalyzedReceiptDTO> getReceiptById(
            @PathVariable("receiptId") int id
    ) {
        AnalyzedReceiptDTO dto =
                mapper.convertToDTO(receiptService.findReceiptById(id));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_CREATE')")
    @PostMapping(consumes = "application/json")
    public SuccessResponse<AnalyzedReceiptDTO> createNewReceipt(
            @RequestBody AnalyzedReceiptForm formData
    ) {
        AnalyzedReceipt receipt = mapper.convertToEntity(formData);

        ClaimRequest request = requestService
                .findRequestById(formData.getClaimRequestId());
        User analyzer = userService
                .findUserById(formData.getAnalyzerId());

        receipt.setClaimRequest(request);
        receipt.setAnalyzer(analyzer);

        AnalyzedReceiptDTO dto =
                mapper.convertToDTO(receiptService.saveReceipt(receipt));

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
        AnalyzedReceipt receipt = receiptService.findReceiptById(id);
        NullAwareBeanUtil.getInstance().copyProperties(receipt, formData);

        if (formData.getClaimRequestId() != null) {
            ClaimRequest request = requestService
                    .findRequestById(formData.getClaimRequestId());
            receipt.setClaimRequest(request);
        }

        if (formData.getAnalyzerId() != null) {
            User analyzer = userService
                    .findUserById(formData.getAnalyzerId());
            receipt.setAnalyzer(analyzer);
        }

        AnalyzedReceiptDTO dto =
                mapper.convertToDTO(receiptService.saveReceipt(receipt));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_UPDATE')")
    @PatchMapping(value = "/{receiptId}/validate")
    public SuccessResponse<Boolean> validateReceipt(
            @PathVariable("receiptId") int id
    ) {
        receiptService.validateReceipt(id);

        return new SuccessResponse<>(true);
    }

    @PreAuthorize("hasAuthority('ANALYZED_RECEIPT_UPDATE')")
    @PatchMapping(value = "/{receiptId}/invalidate")
    public SuccessResponse<Boolean> invalidateReceipt(
            @PathVariable("receiptId") int id
    ) {
        receiptService.invalidateReceipt(id);

        return new SuccessResponse<>(true);
    }
}
