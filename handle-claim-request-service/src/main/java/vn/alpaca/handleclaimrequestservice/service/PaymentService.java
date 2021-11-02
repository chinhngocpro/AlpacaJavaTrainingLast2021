package vn.alpaca.handleclaimrequestservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import vn.alpaca.common.dto.request.CustomerPaymentFilter;
import vn.alpaca.common.dto.request.PaymentFilter;
import vn.alpaca.common.dto.request.PaymentRequest;
import vn.alpaca.common.exception.ResourceNotFoundException;
import vn.alpaca.handleclaimrequestservice.client.CustomerFeignClient;
import vn.alpaca.handleclaimrequestservice.entity.ClaimRequest;
import vn.alpaca.handleclaimrequestservice.entity.Payment;
import vn.alpaca.handleclaimrequestservice.mapper.PaymentMapper;
import vn.alpaca.handleclaimrequestservice.repository.PaymentRepository;
import vn.alpaca.handleclaimrequestservice.repository.spec.PaymentSpec;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    private final ClaimRequestService claimRequestService;
    private final CustomerFeignClient customerClient;

    public Page<Payment> findAllPayments(PaymentFilter filter) {
        return repository.findAll(PaymentSpec.getSpecification(filter),
                                  filter.getPagination().getPageAndSort());
    }


    public Payment findPaymentById(int id) {
        return repository.findById(id)
                         .orElseThrow(() -> new ResourceNotFoundException("Payment id not found."));
    }

    public Page<Payment> findPaymentsByRequestIdAndCustomerIdCard(CustomerPaymentFilter filter) {
        int customerId = customerClient
                .getByIdCardNumber(filter.getIdCardNumber())
                .getData().getId();

        return repository.findAllByRequestIdAndCustomerId(filter.getRequestId(),
                                                          customerId,
                                                          PaymentSpec.getSpecification(filter),
                                                          filter.getPagination().getPageAndSort());
    }

    public Payment createPayment(PaymentRequest requestData) {
        Payment payment = mapper.createPayment(requestData);

        ClaimRequest claimRequest = claimRequestService.findRequestById(requestData.getRequestId());

        payment.setClaimRequest(claimRequest);

        return repository.save(payment);
    }

    public Payment updatePayment(int id, PaymentRequest requestData) {
        Payment payment = findPaymentById(id);
        mapper.updatePayment(payment, requestData);

        ClaimRequest claimRequest = claimRequestService.findRequestById(requestData.getRequestId());

        payment.setClaimRequest(claimRequest);

        return repository.save(payment);
    }
}

