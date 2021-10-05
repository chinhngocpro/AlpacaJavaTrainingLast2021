package vn.alpaca.alpacajavatraininglast2021.controller.v1;

import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.UserDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.UserMapper;
import vn.alpaca.alpacajavatraininglast2021.service.UserService;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.SuccessResponse;

import java.lang.reflect.InvocationTargetException;
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

    @GetMapping(consumes = "application/json", produces = "application/json")
    public SuccessResponse<Page<UserDTO>> getAllUsers(
            @RequestParam("page") Optional<Integer> pageNumber,
            @RequestParam("size") Optional<Integer> pageSize,
            @RequestParam("name") Optional<String> name,
            @RequestParam("active") Optional<Boolean> active,
            @RequestParam("role-name") Optional<String> roleName
    ) {
        Sort sort = Sort.unsorted();

        Pageable pageable = Pageable.unpaged();

        if (pageNumber.isPresent()) {
            pageable = PageRequest.of(pageNumber.get(), pageSize.orElse(5));
        }

        Page<UserDTO> dtoPage = new PageImpl<>(
                service.findAllUsers(pageable)
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
            @RequestBody UserDTO userDTO
    ) throws InvocationTargetException, IllegalAccessException {
        User user = new User();
        notNullUtil.copyProperties(user, userDTO);
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
            @RequestBody UserDTO userDTO
    ) throws InvocationTargetException, IllegalAccessException {
        User user = service.findUserById(id);
        notNullUtil.copyProperties(user, userDTO);

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
