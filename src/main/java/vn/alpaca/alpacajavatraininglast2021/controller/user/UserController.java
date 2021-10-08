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
import vn.alpaca.alpacajavatraininglast2021.service.RoleService;
import vn.alpaca.alpacajavatraininglast2021.service.UserService;
import vn.alpaca.alpacajavatraininglast2021.util.DateUtil;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;
import vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil;
import vn.alpaca.alpacajavatraininglast2021.wrapper.request.user.UserForm;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.SuccessResponse;

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
    private final NullAwareBeanUtil notNullUtil;
    private final DateUtil dateUtil;
    private final RequestParamUtil paramUtil;

    public UserController(UserService userService,
                          RoleService roleService,
                          UserMapper mapper,
                          NullAwareBeanUtil notNullUtil,
                          DateUtil dateUtil,
                          RequestParamUtil paramUtil) {
        this.userService = userService;
        this.roleService = roleService;
        this.mapper = mapper;
        this.notNullUtil = notNullUtil;
        this.dateUtil = dateUtil;
        this.paramUtil = paramUtil;
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping
    public SuccessResponse<Page<UserDTO>> getAllUsers(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestParam(value = "username", required = false)
                    Optional<String> username,
            @RequestParam(value = "full-name", required = false)
                    Optional<String> fullName,
            @RequestParam(value = "gender", required = false)
                    Optional<Boolean> isMale,
            @RequestParam(value = "id-card", required = false)
                    Optional<String> idCardNumber,
            @RequestParam(value = "email", required = false)
                    Optional<String> email,
            @RequestParam(value = "dob-from", required = false)
                    Optional<String> dobFrom,
            @RequestParam(value = "dob-to", required = false)
                    Optional<String> dobTo,
            @RequestParam(value = "address", required = false)
                    Optional<String> address,
            @RequestParam(value = "active", required = false)
                    Optional<Boolean> active,
            @RequestParam(value = "role-name", required = false)
                    Optional<String> roleName
    ) {
        Sort sort = paramUtil.getSort(sortBy);
        Pageable pageable = paramUtil.getPageable(pageNumber, pageSize, sort);

        Page<UserDTO> dtoPage = new PageImpl<>(
                userService.findAllUsers(
                                username.orElse(null),
                                fullName.orElse(null),
                                isMale.orElse(null),
                                idCardNumber.orElse(null),
                                email.orElse(null),
                                dateUtil.convertStringToDate(dobFrom.orElse(null)),
                                dateUtil.convertStringToDate(dobTo.orElse(null)),
                                address.orElse(null),
                                active.orElse(null),
                                roleName.orElse(null),
                                pageable
                        )
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
        System.out.println(userService.findUserById(id));
        UserDTO dto = mapper.convertToDTO(userService.findUserById(id));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('USER_CREATE')")
    @PostMapping(consumes = "application/json")
    public SuccessResponse<UserDTO> createNewUser(
            @RequestBody UserForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        User user = new User();
        notNullUtil.copyProperties(user, formData);

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
            @RequestBody UserForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        User user = userService.findUserById(id);
        notNullUtil.copyProperties(user, formData);

        if (formData.getRoleId() != null) {
            Role userRole = roleService.findRoleById(formData.getRoleId());
            user.setRole(userRole);
        }

        User savedUser = userService.saveUser(user);
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
