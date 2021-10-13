package vn.alpaca.userservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.userservice.object.entity.Role;
import vn.alpaca.userservice.object.dto.RoleDTO;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface RoleMapper {
    RoleDTO convertToDTO(Role role);
}
