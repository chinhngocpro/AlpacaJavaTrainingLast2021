package vn.alpaca.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.common.dto.request.RoleRequest;
import vn.alpaca.common.dto.response.RoleResponse;
import vn.alpaca.common.dto.wrapper.AbstractResponse;
import vn.alpaca.common.dto.wrapper.SuccessResponse;
import vn.alpaca.userservice.mapper.RoleMapper;
import vn.alpaca.userservice.service.RoleService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

  private final RoleService service;
  private final RoleMapper mapper;

  @PreAuthorize("hasAuthority('ROLE_READ')")
  @GetMapping
  AbstractResponse getAllRoles() {
    List<RoleResponse> data =
        service.findAll().stream().map(mapper::roleToRoleResponse).collect(Collectors.toList());

    return new SuccessResponse<>(data);
  }

  @PreAuthorize("hasAuthority('ROLE_READ')")
  @GetMapping("/{roleId}")
  AbstractResponse getRoleById(@PathVariable int roleId) {
    RoleResponse data = mapper.roleToRoleResponse(service.findById(roleId));

    return new SuccessResponse<>(data);
  }

  @PreAuthorize("hasAuthority('ROLE_READ')")
  @GetMapping("_search/name/{roleName}")
  AbstractResponse getRoleByName(@PathVariable String roleName) {
    RoleResponse data = mapper.roleToRoleResponse(service.findByRoleName(roleName));

    return new SuccessResponse<>(data);
  }

  @PreAuthorize("hasAuthority('ROLE_CREATE')")
  @PostMapping
  AbstractResponse createRole(@RequestBody @Valid RoleRequest requestData) {
    RoleResponse data = mapper.roleToRoleResponse(service.create(requestData));

    return new SuccessResponse<>(HttpStatus.CREATED.value(), data);
  }

  @PreAuthorize("hasAuthority('ROLE_UPDATE')")
  @PutMapping("/{roleId}")
  AbstractResponse updateRole(
      @PathVariable int roleId, @RequestBody @Valid RoleRequest requestData) {
    RoleResponse data = mapper.roleToRoleResponse(service.update(roleId, requestData));

    return new SuccessResponse<>(data);
  }

  @PreAuthorize("hasAuthority('ROLE_DELETE')")
  @DeleteMapping("/{roleId}")
  AbstractResponse deleteRole(@PathVariable int roleId) {
    service.delete(roleId);
    return new SuccessResponse<>(null);
  }
}
