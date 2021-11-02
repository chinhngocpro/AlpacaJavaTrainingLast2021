package vn.alpaca.customerservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.common.dto.request.CustomerRequest;
import vn.alpaca.common.dto.response.CustomerResponse;
import vn.alpaca.customerservice.entity.Customer;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CustomerMapper {

  CustomerResponse customerToCustomerResponse(Customer customer);

  Customer convertToEntity(CustomerRequest requestData);

  void updateCustomer(@MappingTarget Customer customer, CustomerRequest requestData);
}
