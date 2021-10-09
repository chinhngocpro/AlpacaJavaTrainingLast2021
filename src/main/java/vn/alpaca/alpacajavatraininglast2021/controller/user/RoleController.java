package vn.alpaca.alpacajavatraininglast2021.controller.user;

import org.springframework.data.domain.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.alpacajavatraininglast2021.object.dto.RoleDTO;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Role;
import vn.alpaca.alpacajavatraininglast2021.object.mapper.RoleMapper;
import vn.alpaca.alpacajavatraininglast2021.object.request.role.RoleForm;
import vn.alpaca.alpacajavatraininglast2021.object.response.SuccessResponse;
import vn.alpaca.alpacajavatraininglast2021.service.RoleService;

import java.util.Optional;

import static vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil.getPageable;
import static vn.alpaca.alpacajavatraininglast2021.util.RequestParamUtil.getSort;

@RestController
@RequestMapping(
        value = "/api/user/roles",
        produces = "application/json"
)
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public RoleController(RoleService roleService,
                          RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @PreAuthorize("hasAuthority('SYSTEM_ROLE_READ')")
    @GetMapping
    public SuccessResponse<Page<RoleDTO>> getAllRoles(
            @RequestParam(value = "page", required = false)
                    Optional<Integer> pageNumber,
            @RequestParam(value = "size", required = false)
                    Optional<Integer> pageSize,
            @RequestParam(value = "sort-by", required = false)
                    Optional<String> sortBy
    ) {
        Sort sort = getSort(sortBy);
        Pageable pageable = getPageable(pageNumber, pageSize, sort);

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
    public SuccessResponse<RoleDTO> updateRole(@PathVariable("id") int id,
                                               @RequestBody RoleForm form) {
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
