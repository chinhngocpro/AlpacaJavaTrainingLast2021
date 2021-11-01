package vn.alpaca.userservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import vn.alpaca.common.dto.response.RoleResponse;
import vn.alpaca.userservice.entity.es.RoleES;
import vn.alpaca.userservice.entity.jpa.Role;

@Mapper(
    componentModel = "spring",
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RoleMapper {

  RoleResponse roleToRoleResponse(Role role);

  RoleES roleToRoleES(Role role);

  Role roleESToRole(RoleES roleES);
}
