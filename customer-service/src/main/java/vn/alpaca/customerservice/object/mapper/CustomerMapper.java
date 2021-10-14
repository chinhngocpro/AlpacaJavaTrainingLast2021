package vn.alpaca.customerservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.customerservice.object.entity.Customer;
import vn.alpaca.customerservice.object.wrapper.request.CustomerRequest;
import vn.alpaca.customerservice.object.wrapper.response.CustomerResponse;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {

    CustomerResponse convertToResponseModel(Customer customer);

    Customer convertToEntity(CustomerRequest requestData);
}
