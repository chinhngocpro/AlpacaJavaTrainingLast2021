package vn.alpaca.alpacajavatraininglast2021.object.mapper;

import org.mapstruct.Mapper;
import vn.alpaca.alpacajavatraininglast2021.object.dto.ContractDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Contract;

@Mapper(componentModel = "spring")
public interface ContractMapper {

    ContractDTO convertToDTO(Contract contract);
}
