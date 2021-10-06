package vn.alpaca.alpacajavatraininglast2021.object.mapper;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import vn.alpaca.alpacajavatraininglast2021.object.dto.RoleDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Authority;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Role;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions", ignore = true)
    RoleDTO convertToDTO(Role role);

    @BeforeMapping
    default void getPermissionList
            (@MappingTarget RoleDTO dto, Role role) {
        Set<String> authorities =
                role.getAuthorities().stream()
                        .map(Authority::getPermissionName)
                        .collect(Collectors.toSet());
        dto.setPermissions(authorities);
    }
}
