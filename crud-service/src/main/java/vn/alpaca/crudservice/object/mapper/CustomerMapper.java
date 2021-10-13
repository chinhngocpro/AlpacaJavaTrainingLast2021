package vn.alpaca.crudservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.crudservice.object.entity.Customer;
import vn.alpaca.crudservice.object.wrapper.dto.CustomerDTO;
import vn.alpaca.crudservice.object.wrapper.request.customer.CustomerForm;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    CustomerDTO convertToDTO(Customer customer);

    Customer convertToEntity(CustomerForm customerForm);
}
