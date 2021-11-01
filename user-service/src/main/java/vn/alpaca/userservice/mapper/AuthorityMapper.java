package vn.alpaca.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.common.dto.response.AuthorityResponse;
import vn.alpaca.userservice.entity.jpa.Authority;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthorityMapper {

  AuthorityResponse authorityToAuthorityResponse(Authority authority);
}
