package vn.alpaca.alpacajavatraininglast2021.controller.v1;

import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.UserDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.UserMapper;
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
        value = "/api/v1/users",
        produces = "application/json"
)
public class UserController {

    private final UserService service;
    private final UserMapper mapper;
    private final NullAwareBeanUtil notNullUtil;
    private final DateUtil dateUtil;
    private final RequestParamUtil paramUtil;

    public UserController(UserService service,
                          UserMapper mapper,
                          NullAwareBeanUtil notNullUtil,
                          DateUtil dateUtil,
                          RequestParamUtil paramUtil) {
        this.service = service;
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
                service.findAllUsers(
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
        UserDTO dto = mapper.convertToDTO(service.findUserById(id));

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('USER_CREATE')")
    @PostMapping(consumes = "application/json")
    public SuccessResponse<UserDTO> createNewUser(
            @RequestBody UserForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        User user = new User();
        notNullUtil.copyProperties(user, formData);

        UserDTO dto = mapper.convertToDTO(service.saveUser(user));

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
        User user = service.findUserById(id);
        notNullUtil.copyProperties(user, formData);

        UserDTO dto = mapper.convertToDTO(user);

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PatchMapping(value = "/{userId}/activate")
    public SuccessResponse<Boolean> activateUser(
            @PathVariable("userId") int id
    ) {
        service.activateUser(id);

        return new SuccessResponse<>(true);
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PatchMapping(value = "/{userId}/deactivate")
    public SuccessResponse<Boolean> deactivateUser(
            @PathVariable("userId") int id
    ) {
        service.deactivateUser(id);

        return new SuccessResponse<>(true);
    }

}
