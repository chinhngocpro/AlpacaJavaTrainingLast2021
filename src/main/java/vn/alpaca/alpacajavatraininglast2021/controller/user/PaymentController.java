package vn.alpaca.alpacajavatraininglast2021.controller.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.PaymentDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.ClaimRequest;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Payment;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.PaymentMapper;
import vn.alpaca.alpacajavatraininglast2021.object.request.payment.PaymentFilter;
import vn.alpaca.alpacajavatraininglast2021.object.request.payment.PaymentForm;
import vn.alpaca.alpacajavatraininglast2021.object.response.SuccessResponse;
import vn.alpaca.alpacajavatraininglast2021.service.ClaimRequestService;
import vn.alpaca.alpacajavatraininglast2021.service.PaymentService;
import vn.alpaca.alpacajavatraininglast2021.service.UserService;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;
import vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping(
        value = "/api/user/payments",
        produces = "application/json"
)
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;
    private final ClaimRequestService requestService;
    private final PaymentMapper mapper;

    public PaymentController(PaymentService paymentService,
                             UserService userService,
                             ClaimRequestService requestService,
                             PaymentMapper mapper) {
        this.paymentService = paymentService;
        this.userService = userService;
        this.requestService = requestService;
        this.mapper = mapper;
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
            @RequestBody Optional<PaymentFilter> filter
    ) {

        Sort sort = RequestParamUtil.getSort(sortBy);
        Pageable pageable =
                RequestParamUtil.getPageable(pageNumber, pageSize, sort);

        Page<PaymentDTO> dtoPage = new PageImpl<>(
                paymentService
                        .findAllPayments(filter.orElse(new PaymentFilter()),
                                pageable)
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @PreAuthorize("hasAuthority('PAYMENT_READ')")
    @GetMapping(
            value = "/{paymentId}",
            consumes = "application/json"
    )
    public SuccessResponse<PaymentDTO> getPaymentById(
            @PathVariable("paymentId") int id
    ) {
        PaymentDTO dto =
                mapper.convertToDTO(paymentService.findPaymentById(id));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('PAYMENT_CREATE')")
    @PostMapping(consumes = "application/json")
    public SuccessResponse<PaymentDTO> createNewPayment(
            @RequestBody PaymentForm formData
    ) {
        Payment payment = mapper.convertToEntity(formData);

        User accountant = userService
                .findUserById(formData.getAccountantId());
        payment.setAccountant(accountant);

        ClaimRequest claimRequest = requestService
                .findRequestById(formData.getRequestId());
        payment.setClaimRequest(claimRequest);

        PaymentDTO dto =
                mapper.convertToDTO(paymentService.savePayment(payment));

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
        Payment payment = paymentService.findPaymentById(id);
        NullAwareBeanUtil.getInstance().copyProperties(payment, formData);

        if (formData.getAccountantId() != null) {
            User accountant = userService
                    .findUserById(formData.getAccountantId());
            payment.setAccountant(accountant);
        }

        if (formData.getRequestId() != null) {
            ClaimRequest claimRequest = requestService
                    .findRequestById(formData.getRequestId());
            payment.setClaimRequest(claimRequest);
        }

        PaymentDTO dto =
                mapper.convertToDTO(paymentService.savePayment(payment));

        return new SuccessResponse<>(dto);
    }
}
