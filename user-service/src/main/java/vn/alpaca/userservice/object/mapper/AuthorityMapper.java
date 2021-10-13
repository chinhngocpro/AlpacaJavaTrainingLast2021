package vn.alpaca.userservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.userservice.object.entity.Authority;
import vn.alpaca.userservice.object.dto.AuthorityDTO;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface AuthorityMapper {

    AuthorityDTO convertToDTO(Authority authority);
}
