package vn.alpaca.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.dto.request.RoleReq;
import vn.alpaca.dto.response.RoleRes;
import vn.alpaca.dto.wrapper.SuccessResponse;
import vn.alpaca.userservice.service.RoleService;

import java.util.List;


@RestController
@RequestMapping(value = "/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService service;

    @PreAuthorize("hasAuthority('SYSTEM_ROLE_READ')")
    @GetMapping
    public SuccessResponse<List<RoleRes>> getAllRoles() {

        List<RoleRes> dtoPage = service.findAllRoles();

        return new SuccessResponse<>(dtoPage);
    }

    @PreAuthorize("hasAuthority('SYSTEM_ROLE_READ')")
    @GetMapping("/{id}")
    public SuccessResponse<RoleRes> getRole(@PathVariable("id") int id) {
        RoleRes dto = service.findRoleById(id);
        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('SYSTEM_ROLE_CREATE')")
    @PostMapping
    public SuccessResponse<RoleRes> createRole(@RequestBody RoleReq form) {
        RoleRes dto = service.createNewRole(form);
        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('SYSTEM_ROLE_UPDATE')")
    @PutMapping(value = "/{id}")
    public SuccessResponse<RoleRes> updateRole(@PathVariable("id") int id,
                                               @RequestBody RoleReq form) {
        RoleRes dto = service.updateRole(id, form);
        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('SYSTEM_ROLE_DELETE')")
    @DeleteMapping("/{id}")
    public SuccessResponse<Void> deleteRole(@PathVariable("id") int id) {
        service.deleteRole(id);
        return new SuccessResponse<>(null);
    }
}