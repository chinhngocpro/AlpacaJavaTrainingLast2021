package vn.alpaca.alpacajavatraininglast2021.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.alpacajavatraininglast2021.object.dto.PaymentDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Payment;
import vn.alpaca.alpacajavatraininglast2021.object.request.payment.PaymentForm;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface PaymentMapper {

    @Mapping(
            target = "claimRequestId",
            expression = "java(payment.getAccountant().getId())")
    @Mapping(
            target = "accountantId",
            expression = "java(payment.getClaimRequest().getId())")
    PaymentDTO convertToDTO(Payment payment);

    Payment convertToEntity(PaymentForm paymentForm);

}
