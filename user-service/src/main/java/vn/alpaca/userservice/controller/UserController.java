package vn.alpaca.userservice.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.userservice.object.mapper.UserMapper;
import vn.alpaca.userservice.object.wrapper.request.UserFilter;
import vn.alpaca.userservice.object.wrapper.response.UserDTO;
import vn.alpaca.userservice.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/users", produces = "application/json")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService,
                          UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public Page<UserDTO> getAllUsers(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody Optional<UserFilter> filter
    ) {
        Page<UserDTO> dtoPage = userService.findAllUsers(
                filter.orElse(new UserFilter()),
                Pageable.unpaged()
        ).map(userMapper::convertToDTO);

        return dtoPage;
    }
}
