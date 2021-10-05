package vn.alpaca.alpacajavatraininglast2021.object.mapper;

import org.mapstruct.Mapper;
import vn.alpaca.alpacajavatraininglast2021.object.dto.RoleDTO;
import vn.alpaca.alpacajavatraininglast2021.object.dto.UserDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;


@Mapper(componentModel = "spring", uses = RoleDTO.class)
public interface UserMapper {

    UserDTO convertToDTO(User user);
}
