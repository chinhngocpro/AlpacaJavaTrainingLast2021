package vn.alpaca.handleclaimrequestservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.handleclaimrequestservice.object.wrapper.request.payment.CustomerPaymentFilter;
import vn.alpaca.handleclaimrequestservice.object.wrapper.request.payment.PaymentFilter;
import vn.alpaca.handleclaimrequestservice.object.wrapper.request.payment.PaymentRequest;
import vn.alpaca.handleclaimrequestservice.object.wrapper.response.PaymentResponse;
import vn.alpaca.handleclaimrequestservice.service.PaymentService;
import vn.alpaca.response.wrapper.SuccessResponse;
import vn.alpaca.util.ExtractParam;

import java.util.Optional;

@RestController
@RequestMapping("/claim-requests")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @GetMapping
    public SuccessResponse<Page<PaymentResponse>> getAllPayments(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "limit", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody Optional<PaymentFilter> filter
    ) {

        Pageable pageable = ExtractParam.getPageable(
                pageNumber, pageSize,
                ExtractParam.getSort(sortBy)
        );

        Page<PaymentResponse> responseData = service.findAllPayments(
                filter.orElse(new PaymentFilter()),
                pageable
        );

        return new SuccessResponse<>(responseData);
    }

    @GetMapping(value = "/customer")
    public SuccessResponse<Page<PaymentResponse>>
    getPaymentByRequestIdAndIdCardNumber(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "limit", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody CustomerPaymentFilter filter
    ) {
        Pageable pageable = ExtractParam.getPageable(
                pageNumber, pageSize,
                ExtractParam.getSort(sortBy)
        );

        Page<PaymentResponse> responseData = service
                .findPaymentsByRequestIdAndCustomerIdCard(
                        filter, pageable
                );

        return new SuccessResponse<>(responseData);
    }

    @GetMapping(value = "/{paymentId}")
    public SuccessResponse<PaymentResponse> getPaymentById(
            @PathVariable("paymentId") int id
    ) {
        PaymentResponse responseData = service.findPaymentById(id);

        return new SuccessResponse<>(responseData);
    }

    @PostMapping
    public SuccessResponse<PaymentResponse> createNewPayment(
            @RequestBody PaymentRequest requestData
    ) {
        PaymentResponse responseData = service.createPayment(requestData);

        return new SuccessResponse<>(responseData);
    }

    @PutMapping(value = "/{paymentId}")
    public SuccessResponse<PaymentResponse> updatePayment(
            @PathVariable("paymentId") int id,
            @RequestBody PaymentRequest requestData
    ) {
        PaymentResponse responseData = service.updatePayment(id, requestData);

        return new SuccessResponse<>(responseData);
    }
}
