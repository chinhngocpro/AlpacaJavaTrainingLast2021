package vn.alpaca.alpacajavatraininglast2021.controller.v1;

import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.UserDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.UserMapper;
import vn.alpaca.alpacajavatraininglast2021.service.UserService;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;
import vn.alpaca.alpacajavatraininglast2021.wrapper.request.user.UserForm;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.SuccessResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;
    private final UserMapper mapper;
    private final NullAwareBeanUtil notNullUtil;

    public UserController(UserService service,
                          UserMapper mapper,
                          NullAwareBeanUtil notNullUtil) {
        this.service = service;
        this.mapper = mapper;
        this.notNullUtil = notNullUtil;
    }

    @GetMapping(
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<Page<UserDTO>> getAllUsers(
            @RequestParam("page") Optional<Integer> pageNumber,
            @RequestParam("size") Optional<Integer> pageSize,
            @RequestParam("sort-by") Optional<String> sortBy,
            @RequestParam("username") Optional<String> username,
            @RequestParam("fullName") Optional<String> fullName,
            @RequestParam("gender") Optional<Boolean> isMale,
            @RequestParam("id-card") Optional<String> idCardNumber,
            @RequestParam("email") Optional<String> email,
            @RequestParam("dob-from") Optional<Date> dobFrom,
            @RequestParam("dob-to") Optional<Date> dobTo,
            @RequestParam("address") Optional<String> address,
            @RequestParam("active") Optional<Boolean> active,
            @RequestParam("role-name") Optional<String> roleName
    ) {
        Sort sort = Sort.unsorted();

        Pageable pageable = Pageable.unpaged();

        if (pageNumber.isPresent()) {
            pageable = PageRequest.of(pageNumber.get(), pageSize.orElse(5));
        }

        Page<UserDTO> dtoPage = new PageImpl<>(
                service.findAllUsers(
                                username.orElse(""),
                                fullName.orElse(""),
                                isMale.orElse(null),
                                idCardNumber.orElse(""),
                                email.orElse(""),
                                dobFrom.orElse(new Date(Long.MIN_VALUE)),
                                dobTo.orElse(new Date(Long.MAX_VALUE)),
                                address.orElse(""),
                                active.orElse(null),
                                roleName.orElse(""),
                                pageable
                        )
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @GetMapping(
            value = "/{userId}",
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<UserDTO> getUserById(
            @PathVariable("userId") int id
    ) {
        UserDTO dto = mapper.convertToDTO(service.findUserById(id));

        return new SuccessResponse<>(dto);
    }

    @PostMapping(
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<UserDTO> createNewUser(
            @RequestBody UserForm formData
    ) throws InvocationTargetException, IllegalAccessException {
        User user = new User();
        notNullUtil.copyProperties(user, formData);
        System.out.println(user);

        UserDTO dto = mapper.convertToDTO(service.saveUser(user));

        return new SuccessResponse<>(dto);
    }

    @PutMapping(
            value = "/{userId}",
            consumes = "application/json",
            produces = "application/json"
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

    @PatchMapping(
            value = "/{userId}/activate",
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<Boolean> activateUser(
            @PathVariable("userId") int id
    ) {
        service.activateUser(id);

        return new SuccessResponse<>(true);
    }

    @PatchMapping(
            value = "/{userId}/deactivate",
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<Boolean> deactivateUser(
            @PathVariable("userId") int id
    ) {
        service.deactivateUser(id);

        return new SuccessResponse<>(true);
    }

}
