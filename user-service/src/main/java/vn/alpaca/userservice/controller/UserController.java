package vn.alpaca.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.common.dto.request.UserFilter;
import vn.alpaca.common.dto.request.UserRequest;
import vn.alpaca.common.dto.response.AuthenticationInfo;
import vn.alpaca.common.dto.response.UserResponse;
import vn.alpaca.common.dto.wrapper.AbstractResponse;
import vn.alpaca.common.dto.wrapper.SuccessResponse;
import vn.alpaca.userservice.mapper.UserMapper;
import vn.alpaca.userservice.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService service;
  private final UserMapper mapper;

  @PreAuthorize("hasAuthority('USER_READ')")
  @GetMapping
  AbstractResponse getAllUser(@RequestBody Optional<UserFilter> filter) {
    Page<UserResponse> response =
        service.findAll(filter.orElse(new UserFilter())).map(mapper::userToUserResponse);

    return new SuccessResponse<>(response);
  }

  @PreAuthorize("hasAuthority('USER_READ')")
  @GetMapping("/{userId}")
  AbstractResponse getUserById(@PathVariable int userId) {
    UserResponse response = mapper.userToUserResponse(service.findById(userId));

    return new SuccessResponse<>(response);
  }

  @GetMapping("/_auth/{username}")
  AbstractResponse getUserByUsername(@PathVariable String username) {
    AuthenticationInfo response = mapper.userToAuthenInfo(service.findByUsername(username));

    return new SuccessResponse<>(response);
  }

  @PreAuthorize("hasAuthority('USER_CREATE')")
  @PostMapping
  AbstractResponse createUser(@RequestBody @Valid UserRequest requestData) {
    UserResponse response = mapper.userToUserResponse(service.create(requestData));

    return new SuccessResponse<>(HttpStatus.CREATED.value(), response);
  }

  @PreAuthorize("hasAuthority('USER_UPDATE')")
  @PutMapping("/{userId}")
  AbstractResponse updateUser(
      @PathVariable int userId, @RequestBody @Valid UserRequest requestData) {
    UserResponse response = mapper.userToUserResponse(service.update(userId, requestData));

    return new SuccessResponse<>(response);
  }

  @PreAuthorize("hasAuthority('USER_UPDATE')")
  @PatchMapping("/{userId}/activate")
  AbstractResponse activateUser(@PathVariable int userId) {
    service.activate(userId);

    return new SuccessResponse<>(null);
  }

  @PreAuthorize("hasAuthority('USER_UPDATE')")
  @PatchMapping("/{userId}/deactivate")
  AbstractResponse deactivateUser(@PathVariable int userId) {
    service.deactivate(userId);

    return new SuccessResponse<>(null);
  }
}
