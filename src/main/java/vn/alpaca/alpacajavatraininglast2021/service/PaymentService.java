package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Payment;
import vn.alpaca.alpacajavatraininglast2021.object.exception.AccessDeniedException;
import vn.alpaca.alpacajavatraininglast2021.object.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.request.payment.PaymentFilter;
import vn.alpaca.alpacajavatraininglast2021.repository.CustomerRepository;
import vn.alpaca.alpacajavatraininglast2021.repository.PaymentRepository;

import static vn.alpaca.alpacajavatraininglast2021.specification.PaymentSpecification.getPaymentSpecification;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;

    public PaymentService(PaymentRepository repository,
                          CustomerRepository customerRepository) {
        this.paymentRepository = repository;
        this.customerRepository = customerRepository;
    }

    public Page<Payment> findAllPayments(
            PaymentFilter filter,
            Pageable pageable
    ) {
        return paymentRepository.findAll(
                getPaymentSpecification(filter),
                pageable
        );
    }


    public Payment findPaymentById(int id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment id not found."
                ));
    }

    public Page<Payment> findPaymentsByRequestIdAndCustomerIdCard(
            int requestId,
            String idCardNumber,
            PaymentFilter filter,
            Pageable pageable
    ) {
        customerRepository.findByIdCardNumber(idCardNumber)
                .orElseThrow(() -> new AccessDeniedException(
                        "Customer identity does not exist."
                ));

        return paymentRepository.findAllByRequestIdAndCustomerIdCard(
                requestId,
                idCardNumber,
                getPaymentSpecification(filter),
                pageable
        );
    }

    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }


}
