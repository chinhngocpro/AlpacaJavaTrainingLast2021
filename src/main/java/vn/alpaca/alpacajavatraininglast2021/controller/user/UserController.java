package vn.alpaca.alpacajavatraininglast2021.controller.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.UserDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Role;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.UserMapper;
import vn.alpaca.alpacajavatraininglast2021.object.request.user.UserFilter;
import vn.alpaca.alpacajavatraininglast2021.object.request.user.UserForm;
import vn.alpaca.alpacajavatraininglast2021.object.response.SuccessResponse;
import vn.alpaca.alpacajavatraininglast2021.service.RoleService;
import vn.alpaca.alpacajavatraininglast2021.service.UserService;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;
import vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil;

import javax.validation.Valid;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

@RestController
@RequestMapping(
        value = "/api/user/users",
        produces = "application/json"
)
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private final UserMapper mapper;

    public UserController(UserService userService,
                          RoleService roleService,
                          UserMapper mapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.mapper = mapper;
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping(consumes = "application/json")
    public SuccessResponse<Page<UserDTO>> getAllUsers(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody Optional<UserFilter> filter
    ) {
        Sort sort = RequestParamUtil.getSort(sortBy);
        Pageable pageable =
                RequestParamUtil.getPageable(pageNumber, pageSize, sort);

        Page<UserDTO> dtoPage = new PageImpl<>(
                userService
                        .findAllUsers(filter.orElse(new UserFilter()), pageable)
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping(value = "/{userId}")
    public SuccessResponse<UserDTO> getUserById(
            @PathVariable("userId") int id
    ) {
        UserDTO dto = mapper.convertToDTO(userService.findUserById(id));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('USER_CREATE')")
    @PostMapping(consumes = "application/json")
    public SuccessResponse<UserDTO> createNewUser(
            @Valid @RequestBody UserForm formData
    ) {

        User user = mapper.convertToEntity(formData);

        if (formData.getRoleId() != null) {
            Role userRole = roleService.findRoleById(formData.getRoleId());
            user.setRole(userRole);
        }

        User savedUser = userService.saveUser(user);
        UserDTO dto = mapper.convertToDTO(savedUser);

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PutMapping(
            value = "/{userId}",
            consumes = "application/json"
    )
    public SuccessResponse<UserDTO> updateUser(
            @PathVariable("userId") int id,
            @RequestBody @Valid UserForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        User target = userService.findUserById(id);
        NullAwareBeanUtil.getInstance()
                .copyProperties(target, formData);

        if (formData.getRoleId() != null) {
            Role userRole = roleService.findRoleById(formData.getRoleId());
            target.setRole(userRole);
        }

        User savedUser = userService.saveUser(target);
        UserDTO dto = mapper.convertToDTO(savedUser);

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PatchMapping(value = "/{userId}/activate")
    public SuccessResponse<Boolean> activateUser(
            @PathVariable("userId") int id
    ) {
        userService.activateUser(id);

        return new SuccessResponse<>(true);
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PatchMapping(value = "/{userId}/deactivate")
    public SuccessResponse<Boolean> deactivateUser(
            @PathVariable("userId") int id
    ) {
        userService.deactivateUser(id);

        return new SuccessResponse<>(true);
    }

}
