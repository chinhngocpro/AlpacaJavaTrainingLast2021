package vn.alpaca.handleclaimrequestservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.common.dto.request.CustomerPaymentFilter;
import vn.alpaca.common.dto.request.PaymentFilter;
import vn.alpaca.common.dto.request.PaymentRequest;
import vn.alpaca.common.dto.response.PaymentResponse;
import vn.alpaca.common.dto.wrapper.AbstractResponse;
import vn.alpaca.common.dto.wrapper.SuccessResponse;
import vn.alpaca.handleclaimrequestservice.entity.Payment;
import vn.alpaca.handleclaimrequestservice.mapper.PaymentMapper;
import vn.alpaca.handleclaimrequestservice.service.PaymentService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;
    private final PaymentMapper mapper;

    @PreAuthorize("hasAuthority('PAYMENT_READ')")
    @GetMapping
    AbstractResponse getAllPayments(@RequestBody Optional<PaymentFilter> filter) {
        Page<Payment> payments = service.findAllPayments(filter.orElse(new PaymentFilter()));
        Page<PaymentResponse> response = payments.map(mapper::convertToResponseModel);

        return new SuccessResponse<>(response);
    }

    @GetMapping(value = "/customer")
    AbstractResponse getPaymentByRequestIdAndIdCardNumber(@RequestBody CustomerPaymentFilter filter) {
        Page<Payment> payments = service.findPaymentsByRequestIdAndCustomerIdCard(filter);
        Page<PaymentResponse> response = payments.map(mapper::convertToResponseModel);

        return new SuccessResponse<>(response);
    }

    @PreAuthorize("hasAuthority('PAYMENT_READ')")
    @GetMapping(value = "/{paymentId}")
    AbstractResponse getPaymentById(@PathVariable("paymentId") int id) {
        Payment payment = service.findPaymentById(id);
        PaymentResponse response = mapper.convertToResponseModel(payment);

        return new SuccessResponse<>(response);
    }

    @PreAuthorize("hasAuthority('PAYMENT_CREATE')")
    @PostMapping
    AbstractResponse createNewPayment(@RequestBody @Valid PaymentRequest requestData) {
        Payment payment = service.createPayment(requestData);
        PaymentResponse response = mapper.convertToResponseModel(payment);

        return new SuccessResponse<>(response);
    }

    @PreAuthorize("hasAuthority('PAYMENT_UPDATE')")
    @PutMapping(value = "/{paymentId}")
    AbstractResponse updatePayment(@PathVariable("paymentId") int id,
                                   @RequestBody @Valid PaymentRequest requestData) {
        Payment payment = service.updatePayment(id, requestData);
        PaymentResponse response = mapper.convertToResponseModel(payment);

        return new SuccessResponse<>(response);
    }
}
