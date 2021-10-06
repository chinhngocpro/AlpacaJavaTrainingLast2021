package vn.alpaca.alpacajavatraininglast2021.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.PaymentDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Payment;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.PaymentMapper;
import vn.alpaca.alpacajavatraininglast2021.service.PaymentService;
import vn.alpaca.alpacajavatraininglast2021.util.DateUtil;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;
import vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil;
import vn.alpaca.alpacajavatraininglast2021.wrapper.request.payment.PaymentForm;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.SuccessResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping(
        value = "/api/v1/payments",
        produces = "application/json"
)
public class PaymentController {

    private final PaymentService service;
    private final PaymentMapper mapper;
    private final NullAwareBeanUtil notNullUtil;
    private final DateUtil dateUtil;
    private final RequestParamUtil paramUtil;

    public PaymentController(PaymentService service,
                             PaymentMapper mapper,
                             NullAwareBeanUtil notNullUtil,
                             DateUtil dateUtil,
                             RequestParamUtil paramUtil) {
        this.service = service;
        this.mapper = mapper;
        this.notNullUtil = notNullUtil;
        this.dateUtil = dateUtil;
        this.paramUtil = paramUtil;
    }

    @PreAuthorize("hasAuthority('PAYMENT_READ')")
    @GetMapping
    public SuccessResponse<Page<PaymentDTO>> getAllPayments(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestParam(value = "min-amount", required = false)
                    Optional<Double> minAmount,
            @RequestParam(value = "max-amount", required = false)
                    Optional<Double> maxAmount,
            @RequestParam(value = "from-date", required = false)
                    Optional<String> fromDate,
            @RequestParam(value = "to-date", required = false)
                    Optional<String> toDate
    ) {

        Sort sort = paramUtil.getSort(sortBy);
        Pageable pageable = paramUtil.getPageable(pageNumber, pageSize, sort);

        Page<PaymentDTO> dtoPage = new PageImpl<>(
                service.findAllPayments(
                                minAmount.orElse(null),
                                maxAmount.orElse(null),
                                dateUtil.convertStringToDate(fromDate.orElse(null)),
                                dateUtil.convertStringToDate(toDate.orElse(null)),
                                pageable
                        )
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @PreAuthorize("hasAuthority('PAYMENT_CREATE')")
    @PostMapping(consumes = "application/json")
    public SuccessResponse<PaymentDTO> createNewPayment(
            @RequestBody PaymentForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        Payment payment = new Payment();
        notNullUtil.copyProperties(payment, formData);

        PaymentDTO dto =
                mapper.convertToDTO(service.savePayment(payment));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('PAYMENT_UPDATE')")
    @PutMapping(
            value = "/{paymentId}",
            consumes = "application/json"
    )
    public SuccessResponse<PaymentDTO> updatePayment(
            @PathVariable("paymentId") int id,
            @RequestBody PaymentForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        Payment payment = service.findPaymentById(id);
        notNullUtil.copyProperties(payment, formData);

        PaymentDTO dto =
                mapper.convertToDTO(service.savePayment(payment));

        return new SuccessResponse<>(dto);
    }
}
