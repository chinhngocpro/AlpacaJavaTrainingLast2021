package vn.alpaca.alpacajavatraininglast2021.object.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import vn.alpaca.alpacajavatraininglast2021.object.dto.UserDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO convertToDTO(User user);

}
