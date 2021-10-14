package vn.alpaca.contractservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.contractservice.object.entity.Contract;
import vn.alpaca.contractservice.object.wrapper.request.ContractRequest;
import vn.alpaca.contractservice.object.wrapper.response.ContractResponse;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ContractMapper {

    ContractResponse convertToResponseModel(Contract contract);

    Contract convertToEntity(ContractRequest requestData);
}
