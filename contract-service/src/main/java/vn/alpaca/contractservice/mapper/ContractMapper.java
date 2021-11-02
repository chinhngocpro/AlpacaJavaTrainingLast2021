package vn.alpaca.contractservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.common.dto.request.ContractRequest;
import vn.alpaca.common.dto.response.ContractResponse;
import vn.alpaca.contractservice.entity.Contract;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ContractMapper {

  ContractResponse convertToResponseModel(Contract contract);

  Contract createContract(ContractRequest requestData);

  void updateContract(@MappingTarget Contract contract, ContractRequest request);
}
