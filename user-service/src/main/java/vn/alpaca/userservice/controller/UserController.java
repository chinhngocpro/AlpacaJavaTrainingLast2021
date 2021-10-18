package vn.alpaca.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.response.wrapper.SuccessResponse;
import vn.alpaca.userservice.object.dto.AuthDTO;
import vn.alpaca.userservice.object.dto.UserDTO;
import vn.alpaca.userservice.object.entity.Role;
import vn.alpaca.userservice.object.entity.User;
import vn.alpaca.userservice.object.mapper.UserMapper;
import vn.alpaca.userservice.object.request.UserFilter;
import vn.alpaca.userservice.object.request.UserForm;
import vn.alpaca.userservice.service.RoleService;
import vn.alpaca.userservice.service.UserService;
import vn.alpaca.util.ExtractParam;
import vn.alpaca.util.NullAware;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping
    public SuccessResponse<Page<UserDTO>> getAllUsers(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "limit", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody Optional<UserFilter> filter
    ) {
        Pageable pageable = ExtractParam.getPageable(
                pageNumber,
                pageSize,
                ExtractParam.getSort(sortBy)
        );
        Page<UserDTO> dtoPage = userService.findAllUsers(
                filter.orElse(new UserFilter()),
                pageable
        ).map(userMapper::convertToUserDTO);

        return new SuccessResponse<>(dtoPage);
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping("/{userId}")
    public SuccessResponse<UserDTO> getUserById(
            @PathVariable("userId") int id
    ) {
        UserDTO dto = userMapper
                .convertToUserDTO(userService.findUserById(id));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping("/search/username")
    public SuccessResponse<AuthDTO> getUserByUsername(
            @RequestParam("val") String username
    ) {
        AuthDTO dto = userMapper
                .convertToAuthDTO(userService.findUserByUsername(username));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('USER_CREATE')")
    @PostMapping
    public SuccessResponse<UserDTO> createNewUser(
            @Valid @RequestBody UserForm formData
    ) {
        User user = userMapper.convertToEntity(formData);

        if (formData.getRoleId() != null) {
            Role userRole = roleService.findRoleById(formData.getRoleId());
            user.setRole(userRole);
        }

        User savedUser = userService.saveUser(user);
        UserDTO dto = userMapper.convertToUserDTO(savedUser);

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PutMapping(value = "/{userId}")
    public SuccessResponse<UserDTO> updateUser(
            @PathVariable("userId") int id,
            @RequestBody @Valid UserForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        User target = userService.findUserById(id);
        NullAware.getInstance()
                .copyProperties(target, formData);

        if (formData.getRoleId() != null) {
            Role userRole = roleService.findRoleById(formData.getRoleId());
            target.setRole(userRole);
        }

        User savedUser = userService.saveUser(target);
        UserDTO dto = userMapper.convertToUserDTO(savedUser);

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @PatchMapping(value = "/{userId}/activate")
    public SuccessResponse<Boolean> activateUser(
            @PathVariable("userId") int id
    ) {
        userService.activateUser(id);

        return new SuccessResponse<>(true);
    }

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @PatchMapping(value = "/{userId}/deactivate")
    public SuccessResponse<Boolean> deactivateUser(
            @PathVariable("userId") int id
    ) {
        userService.deactivateUser(id);

        return new SuccessResponse<>(true);
    }
}
