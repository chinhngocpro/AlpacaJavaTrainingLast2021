package vn.alpaca.assetsservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.assetsservice.object.entity.Customer;
import vn.alpaca.assetsservice.object.wrapper.dto.CustomerDTO;
import vn.alpaca.assetsservice.object.wrapper.request.customer.CustomerForm;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    CustomerDTO convertToDTO(Customer customer);

    Customer convertToEntity(CustomerForm customerForm);
}
