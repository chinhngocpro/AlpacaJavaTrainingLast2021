package vn.alpaca.alpacajavatraininglast2021.object.mapper;

import org.mapstruct.Mapper;
import vn.alpaca.alpacajavatraininglast2021.object.dto.CustomerDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO convertToDTO(Customer customer);
}
