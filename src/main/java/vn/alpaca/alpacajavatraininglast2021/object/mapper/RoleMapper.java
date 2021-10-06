package vn.alpaca.alpacajavatraininglast2021.object.mapper;

import org.mapstruct.Mapper;
import vn.alpaca.alpacajavatraininglast2021.object.dto.RoleDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    
    RoleDTO convertToDTO(Role role);
}
