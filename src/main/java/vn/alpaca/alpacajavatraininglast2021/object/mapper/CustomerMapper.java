package vn.alpaca.alpacajavatraininglast2021.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.alpacajavatraininglast2021.object.dto.CustomerDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Customer;
import vn.alpaca.alpacajavatraininglast2021.object.request.customer.CustomerForm;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    CustomerDTO convertToDTO(Customer customer);

    Customer convertToEntity(CustomerForm customerForm);
}
