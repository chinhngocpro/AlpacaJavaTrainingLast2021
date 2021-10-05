package vn.alpaca.alpacajavatraininglast2021.controller.v1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            @RequestParam("size") Optional<Integer> pageSize
    ) {
        Pageable pageable = Pageable.unpaged();

        if (pageNumber.isPresent()) {
            pageable = PageRequest.of(pageNumber.get(), pageSize.orElse(5));
        }

        Page<UserDTO> userPage = new PageImpl<>(
                service.findAllUsers(pageable)
                        .map(mapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(userPage);
    }

    @GetMapping(value = "/{userId}",
            consumes = "application/json",
            produces = "application/json"
    )
    public SuccessResponse<UserDTO> getUserById(
            @PathVariable("userId") int id
    ) {
        UserDTO user = mapper.convertToDTO(service.findUserById(id));

        return new SuccessResponse<>(user);
    }

    @PostMapping(consumes = "application/json",  produces = "application/json")
    public SuccessResponse<UserDTO> createNewUser(
            @RequestBody UserDTO userDTO
    ) throws InvocationTargetException, IllegalAccessException {
        System.out.println(userDTO);
        User user = new User();
        notNullUtil.copyProperties(user, userDTO);
        System.out.println(user);

        return new SuccessResponse<>(null);
    }

}
