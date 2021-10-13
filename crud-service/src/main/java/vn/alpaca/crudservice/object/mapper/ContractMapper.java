package vn.alpaca.crudservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.crudservice.object.entity.Contract;
import vn.alpaca.crudservice.object.wrapper.dto.ContractDTO;
import vn.alpaca.crudservice.object.wrapper.request.contract.ContractForm;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface ContractMapper {

    ContractDTO convertToDTO(Contract contract);

    Contract convertToEntity(ContractForm contractForm);
}
