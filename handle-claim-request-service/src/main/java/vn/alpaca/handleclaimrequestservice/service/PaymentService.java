package vn.alpaca.handleclaimrequestservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.handleclaimrequestservice.client.CustomerFeignClient;
import vn.alpaca.handleclaimrequestservice.object.entity.Payment;
import vn.alpaca.handleclaimrequestservice.object.entity.Payment_;
import vn.alpaca.handleclaimrequestservice.object.mapper.PaymentMapper;
import vn.alpaca.handleclaimrequestservice.object.wrapper.request.payment.CustomerPaymentFilter;
import vn.alpaca.handleclaimrequestservice.object.wrapper.request.payment.PaymentFilter;
import vn.alpaca.handleclaimrequestservice.object.wrapper.request.payment.PaymentRequest;
import vn.alpaca.handleclaimrequestservice.object.wrapper.response.PaymentResponse;
import vn.alpaca.handleclaimrequestservice.repository.ClaimRequestRepository;
import vn.alpaca.handleclaimrequestservice.repository.PaymentRepository;
import vn.alpaca.response.exception.ResourceNotFoundException;
import vn.alpaca.util.NullAware;

import java.util.Date;
import java.util.Optional;

import static vn.alpaca.handleclaimrequestservice.service.PaymentSpecification.getPaymentSpecification;

@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;

    private final ClaimRequestRepository requestRepository;

    private final CustomerFeignClient customerClient;

    public PaymentService(PaymentRepository repository,
                          PaymentMapper mapper,
                          ClaimRequestRepository requestRepository,
                          CustomerFeignClient customerClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.requestRepository = requestRepository;
        this.customerClient = customerClient;
    }

    public Page<PaymentResponse> findAllPayments(
            PaymentFilter filter,
            Pageable pageable
    ) {
        return repository.findAll(
                getPaymentSpecification(filter),
                pageable
        ).map(mapper::convertToResponseModel);
    }


    public PaymentResponse findPaymentById(int id) {
        Payment payment = preHandleGetObject(repository.findById(id));
        return mapper.convertToResponseModel(payment);
    }

    public Page<PaymentResponse> findPaymentsByRequestIdAndCustomerIdCard(
            CustomerPaymentFilter filter,
            Pageable pageable
    ) {
        int customerId = customerClient
                .getByIdCardNumber(filter.getIdCardNumber())
                .getData().getId();

        return repository.findAllByRequestIdAndCustomerId(
                filter.getRequestId(),
                customerId,
                getPaymentSpecification(filter),
                pageable
        ).map(mapper::convertToResponseModel);
    }

    public PaymentResponse createPayment(PaymentRequest requestData) {
        Payment payment = mapper.convertToEntity(requestData);
        payment.setClaimRequest(
                requestRepository.findById(requestData.getRequestId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Claim request not found"
                        ))
        );

        return mapper.convertToResponseModel(repository.save(payment));
    }

    public PaymentResponse updatePayment(int id, PaymentRequest requestData) {
        Payment payment = preHandleGetObject(repository.findById(id));

        try {
            NullAware.getInstance().copyProperties(payment, requestData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        payment.setClaimRequest(
                requestRepository.findById(requestData.getRequestId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Claim request not found"
                        ))
        );

        return mapper.convertToResponseModel(repository.save(payment));
    }

    private Payment preHandleGetObject(Optional<Payment> optional) {
        return optional.orElseThrow(() -> new ResourceNotFoundException(
                "Payment id not found."
        ));
    }
}

final class PaymentSpecification {

    public static Specification<Payment>
    getPaymentSpecification(PaymentFilter filter) {
        return Specification
                .where(hasAmountBetween(
                        filter.getMinAmount(),
                        filter.getMaxAmount()
                ))
                .and(hasDateBetween(
                        filter.getFromDate(),
                        filter.getToDate()
                ));
    }

    private static Specification<Payment>
    hasAmountBetween(Double min, Double max) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(min) && ObjectUtils.isEmpty(max) ?
                        builder.conjunction() :
                        ObjectUtils.isEmpty(min) ?
                                builder.lessThanOrEqualTo(
                                        root.get(Payment_.AMOUNT),
                                        max
                                ) :
                                ObjectUtils.isEmpty(max) ?
                                        builder.greaterThanOrEqualTo(
                                                root.get(Payment_.AMOUNT),
                                                min
                                        ) :
                                        builder.between(
                                                root.get(Payment_.AMOUNT),
                                                min,
                                                max
                                        );
    }

    private static Specification<Payment>
    hasDateBetween(Date from, Date to) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(from) && ObjectUtils.isEmpty(to) ?
                        builder.conjunction() :
                        ObjectUtils.isEmpty(from) ?
                                builder.lessThanOrEqualTo(
                                        root.get(Payment_.PAYMENT_DATE),
                                        to
                                ) :
                                ObjectUtils.isEmpty(to) ?
                                        builder.greaterThanOrEqualTo(
                                                root.get(Payment_.PAYMENT_DATE),
                                                from
                                        ) :
                                        builder.between(
                                                root.get(Payment_.PAYMENT_DATE),
                                                from,
                                                to
                                        );
    }
}

