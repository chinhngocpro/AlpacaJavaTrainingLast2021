package vn.alpaca.userservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.dto.request.UserReq;
import vn.alpaca.dto.response.AuthenticationRes;
import vn.alpaca.dto.response.UserRes;
import vn.alpaca.userservice.object.entity.User;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "role", expression = "java(user.getRole().getName())")
    @Mapping(target = "roleId", expression = "java(user.getRole().getId())")
    UserRes convertToResModel(User user);

    @Mapping(target = "roleId", expression = "java(user.getRole().getId())")
    AuthenticationRes convertToAuthModel(User user);

    User convertToEntity(UserReq req);

}
