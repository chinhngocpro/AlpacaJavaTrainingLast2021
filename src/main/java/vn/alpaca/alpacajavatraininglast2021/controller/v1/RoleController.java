package vn.alpaca.alpacajavatraininglast2021.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.RoleDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Role;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.RoleMapper;
import vn.alpaca.alpacajavatraininglast2021.service.RoleService;
import vn.alpaca.alpacajavatraininglast2021.util.NullAwareBeanUtil;
import vn.alpaca.alpacajavatraininglast2021.wrapper.request.role.RoleForm;
import vn.alpaca.alpacajavatraininglast2021.wrapper.response.SuccessResponse;

import java.util.Optional;

@RestController
@RequestMapping(
        value = "/api/v1/roles",
        produces = "application/json"
)
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    NullAwareBeanUtil notNullUtil;

    @PreAuthorize("hasAuthority('SYSTEM_ROLE_READ')")
    @GetMapping
    public SuccessResponse<Page<RoleDTO>> getAllRoles(@RequestParam("page") Optional<Integer> pageNumber, @RequestParam("size") Optional<Integer> pageSize) {
        Pageable pageable = Pageable.unpaged();

        if (pageNumber.isPresent()) {
            pageable = PageRequest.of(pageNumber.get(), pageSize.orElse(5));
        }

        Page<RoleDTO> dtoPage = new PageImpl<>(
                roleService.findAllRoles(pageable)
                        .map(roleMapper::convertToDTO)
                        .getContent()
        );

        return new SuccessResponse<>(dtoPage);
    }

    @PreAuthorize("hasAuthority('SYSTEM_ROLE_READ')")
    @GetMapping("/{id}")
    public SuccessResponse<RoleDTO> getRole(@PathVariable("id") int id) {
        Role role = roleService.findRoleById(id);
        RoleDTO dto = roleMapper.convertToDTO(role);
        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('SYSTEM_ROLE_CREATE')")
    @PostMapping(consumes = "application/json")
    public SuccessResponse<RoleDTO> createRole(@RequestBody RoleForm form) {
        Role role = roleService.createNewRole(form);
        RoleDTO dto = roleMapper.convertToDTO(role);
        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('SYSTEM_ROLE_UPDATE')")
    @PutMapping(value = "/{id}", consumes = "application/json")
    public SuccessResponse<RoleDTO> updateRole(@PathVariable("id") int id, @RequestBody RoleForm form) {
        Role role = roleService.updateRole(id, form);
        RoleDTO dto = roleMapper.convertToDTO(role);
        return new SuccessResponse<>(dto);
    }

    @PreAuthorize("hasAuthority('SYSTEM_ROLE_DELETE')")
    @DeleteMapping("/{id}")
    public SuccessResponse<Void> deleteRole(@PathVariable("id") int id) {
        roleService.deleteRole(id);
        return new SuccessResponse<>(null);
    }
}
