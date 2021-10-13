package vn.alpaca.assetsservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.assetsservice.object.entity.ClaimRequest;
import vn.alpaca.assetsservice.object.entity.Payment;
import vn.alpaca.assetsservice.object.mapper.PaymentMapper;
import vn.alpaca.assetsservice.object.wrapper.dto.PaymentDTO;
import vn.alpaca.assetsservice.object.wrapper.request.payment.PaymentFilter;
import vn.alpaca.assetsservice.object.wrapper.request.payment.PaymentForm;
import vn.alpaca.assetsservice.service.ClaimRequestService;
import vn.alpaca.assetsservice.service.PaymentService;
import vn.alpaca.response.wrapper.SuccessResponse;
import vn.alpaca.util.ExtractParam;
import vn.alpaca.util.NullAware;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/assets/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final ClaimRequestService requestService;
    private final PaymentMapper mapper;

    public PaymentController(PaymentService paymentService,
                             ClaimRequestService requestService,
                             PaymentMapper mapper) {
        this.paymentService = paymentService;
        this.requestService = requestService;
        this.mapper = mapper;
    }

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

        Sort sort = ExtractParam.getSort(sortBy);
        Pageable pageable =
                ExtractParam.getPageable(pageNumber, pageSize, sort);

        Page<PaymentDTO> dtoPage = new PageImpl<>(
                paymentService
                        .findAllPayments(filter.orElse(new PaymentFilter()),
                                pageable)
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

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

    @PostMapping(consumes = "application/json")
    public SuccessResponse<PaymentDTO> createNewPayment(
            @RequestBody PaymentForm formData
    ) {
        Payment payment = mapper.convertToEntity(formData);

        ClaimRequest claimRequest = requestService
                .findRequestById(formData.getRequestId());
        payment.setClaimRequest(claimRequest);

        PaymentDTO dto =
                mapper.convertToDTO(paymentService.savePayment(payment));

        return new SuccessResponse<>(dto);
    }

    @PutMapping(
            value = "/{paymentId}",
            consumes = "application/json"
    )
    public SuccessResponse<PaymentDTO> updatePayment(
            @PathVariable("paymentId") int id,
            @RequestBody PaymentForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        Payment payment = paymentService.findPaymentById(id);
        NullAware.getInstance().copyProperties(payment, formData);

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
