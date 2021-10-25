package vn.alpaca.userservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.dto.request.AuthorityRes;
import vn.alpaca.userservice.object.entity.Authority;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface AuthorityMapper {

    AuthorityRes convertToDTO(Authority authority);
}
