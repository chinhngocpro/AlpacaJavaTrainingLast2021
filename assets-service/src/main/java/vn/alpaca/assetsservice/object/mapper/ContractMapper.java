package vn.alpaca.assetsservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.assetsservice.object.entity.Contract;
import vn.alpaca.assetsservice.object.wrapper.dto.ContractDTO;
import vn.alpaca.assetsservice.object.wrapper.request.contract.ContractForm;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface ContractMapper {

    ContractDTO convertToDTO(Contract contract);

    Contract convertToEntity(ContractForm contractForm);
}
