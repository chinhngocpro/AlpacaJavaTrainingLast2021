package vn.alpaca.userservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.userservice.object.entity.User;
import vn.alpaca.userservice.object.dto.UserDTO;
import vn.alpaca.userservice.object.request.UserForm;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "role", expression = "java(user.getRole().getName())")
    UserDTO convertToDTO(User user);

    User convertToEntity(UserForm userForm);

}
