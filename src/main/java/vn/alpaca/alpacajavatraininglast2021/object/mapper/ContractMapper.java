package vn.alpaca.alpacajavatraininglast2021.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.alpacajavatraininglast2021.object.dto.ContractDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Contract;
import vn.alpaca.alpacajavatraininglast2021.object.request.contract.ContractForm;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface ContractMapper {

    ContractDTO convertToDTO(Contract contract);

    Contract convertToEntity(ContractForm contractForm);
}
