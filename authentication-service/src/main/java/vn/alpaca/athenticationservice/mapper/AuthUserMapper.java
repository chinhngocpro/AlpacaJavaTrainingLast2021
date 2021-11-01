package vn.alpaca.athenticationservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.common.dto.response.UserResponse;
import vn.alpaca.security.model.AuthUser;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthUserMapper {
    UserResponse authUserToUserResponse(AuthUser user);
}
