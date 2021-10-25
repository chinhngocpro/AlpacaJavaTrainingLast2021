package vn.alpaca.service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.dto.request.UserFilter;
import vn.alpaca.dto.request.UserReq;
import vn.alpaca.dto.response.UserRes;
import vn.alpaca.dto.wrapper.SuccessResponse;
import vn.alpaca.userservice.service.UserService;
import vn.alpaca.util.ExtractParam;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping
    public SuccessResponse<Page<UserRes>> getAllUsers(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "limit", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy,
            @RequestBody Optional<UserFilter> filter
    ) {
        System.out.println(true);
        Pageable pageable = ExtractParam.getPageable(
                pageNumber,
                pageSize,
                ExtractParam.getSort(sortBy)
        );
        Page<UserRes> dtoPage = service.findAllUsers(
                filter.orElse(new UserFilter()),
                pageable
        );

        return new SuccessResponse<>(dtoPage);
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping("/{userId}")
    public SuccessResponse<UserRes> getUserById(
            @PathVariable("userId") int id
    ) {
        UserRes dto = service.findUserById(id);

        return new SuccessResponse<>(dto);
    }


    @PreAuthorize("hasAuthority('USER_CREATE')")
    @PostMapping
    public SuccessResponse<UserRes> createNewUser(
            @Valid @RequestBody UserReq req
    ) {
        UserRes dto = service.createUser(req);

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PutMapping(value = "/{userId}")
    public SuccessResponse<UserRes> updateUser(
            @PathVariable("userId") int id,
            @RequestBody @Valid UserReq req
    ) {
        UserRes dto = service.updateUser(id, req);

        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @PatchMapping(value = "/{userId}/activate")
    public SuccessResponse<Boolean> activateUser(
            @PathVariable("userId") int id
    ) {
        service.activateUser(id);

        return new SuccessResponse<>(true);
    }

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @PatchMapping(value = "/{userId}/deactivate")
    public SuccessResponse<Boolean> deactivateUser(
            @PathVariable("userId") int id
    ) {
        service.deactivateUser(id);

        return new SuccessResponse<>(true);
    }
}
