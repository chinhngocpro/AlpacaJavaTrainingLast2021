package vn.alpaca.userservice.object.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.dto.response.RoleRes;
import vn.alpaca.userservice.object.entity.Role;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy
                = NullValuePropertyMappingStrategy.IGNORE
)
public interface RoleMapper {
    RoleRes convertToResModel(Role role);
}
