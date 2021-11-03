package vn.alpaca.userservice.mapper;

import org.mapstruct.*;
import vn.alpaca.common.dto.request.UserRequest;
import vn.alpaca.common.dto.response.AuthenticationInfo;
import vn.alpaca.common.dto.response.UserResponse;
import vn.alpaca.security.model.AuthUser;
import vn.alpaca.userservice.entity.es.UserES;
import vn.alpaca.userservice.entity.jpa.Authority;
import vn.alpaca.userservice.entity.jpa.User;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserResponse userToUserResponse(User user);

    UserResponse userESToUserResponse(UserES userES);

    @Mapping(target = "authorities", ignore = true)
    AuthenticationInfo userToAuthenInfo(User user);

    @Mapping(target = "authorities", ignore = true)
    AuthUser userToAuthUser(User user);

    UserES userToUserES(User user);

    User userRequestToUser(UserRequest requestData);

    User userESToUser(UserES userES);

    void updateUser(@MappingTarget User user, UserRequest requestData);

    @AfterMapping
    default void getAuthPermissions(@MappingTarget AuthenticationInfo authInfo, User user) {
        Set<String> authorities =
                user.getRole().getAuthorities().stream()
                    .map(Authority::getPermissionName)
                    .collect(Collectors.toSet());
        authInfo.setAuthorities(authorities);

        String role = user.getRole().getName();
        authInfo.setRoleName(role);
    }

    @AfterMapping
    default void getResponseRoleNameFromUserEntity(@MappingTarget UserResponse response, User user) {
        response.setRoleName(user.getRole().getName());
    }
}
