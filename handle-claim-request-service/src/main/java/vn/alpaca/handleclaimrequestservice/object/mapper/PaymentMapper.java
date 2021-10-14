package vn.alpaca.handleclaimrequestservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.handleclaimrequestservice.object.entity.Payment;
import vn.alpaca.handleclaimrequestservice.object.wrapper.response.PaymentResponse;
import vn.alpaca.handleclaimrequestservice.object.wrapper.request.payment.PaymentRequest;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface PaymentMapper {

    @Mapping(
            target = "claimRequestId",
            expression = "java(payment.getClaimRequest().getId())")
    PaymentResponse convertToResponseModel(Payment payment);

    Payment convertToEntity(PaymentRequest paymentRequest);

}
