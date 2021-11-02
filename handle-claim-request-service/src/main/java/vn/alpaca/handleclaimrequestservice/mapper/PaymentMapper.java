package vn.alpaca.handleclaimrequestservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.common.dto.request.PaymentRequest;
import vn.alpaca.common.dto.response.PaymentResponse;
import vn.alpaca.handleclaimrequestservice.entity.Payment;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PaymentMapper {

    @Mapping(target = "claimRequestId",
            expression = "java(payment.getClaimRequest().getId())")
    PaymentResponse convertToResponseModel(Payment payment);

    Payment createPayment(PaymentRequest paymentRequest);

    void updatePayment(@MappingTarget Payment payment, PaymentRequest requestData);

}
