package vn.alpaca.alpacajavatraininglast2021.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.alpaca.alpacajavatraininglast2021.object.dto.AuthorityDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Authority;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {

     AuthorityDTO convertToDTO(Authority authority);
}
