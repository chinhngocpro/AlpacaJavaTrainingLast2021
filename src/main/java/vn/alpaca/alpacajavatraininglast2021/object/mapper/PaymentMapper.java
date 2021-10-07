package vn.alpaca.alpacajavatraininglast2021.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.alpaca.alpacajavatraininglast2021.object.dto.PaymentDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDTO convertToDTO(Payment payment);
}
