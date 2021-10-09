package vn.alpaca.alpacajavatraininglast2021.object.mapper;

import org.mapstruct.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.UserDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;
import vn.alpaca.alpacajavatraininglast2021.object.request.user.UserForm;

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
