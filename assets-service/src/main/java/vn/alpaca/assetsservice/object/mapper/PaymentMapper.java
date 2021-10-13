package vn.alpaca.assetsservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.assetsservice.object.entity.Payment;
import vn.alpaca.assetsservice.object.wrapper.dto.PaymentDTO;
import vn.alpaca.assetsservice.object.wrapper.request.payment.PaymentForm;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface PaymentMapper {

    @Mapping(
            target = "claimRequestId",
            expression = "java(payment.getClaimRequest().getId())")
    PaymentDTO convertToDTO(Payment payment);

    Payment convertToEntity(PaymentForm paymentForm);

}
