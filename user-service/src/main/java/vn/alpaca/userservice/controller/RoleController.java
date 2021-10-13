package vn.alpaca.userservice.controller;

import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import vn.alpaca.userservice.object.entity.Role;
import vn.alpaca.response.wrapper.SuccessResponse;
import vn.alpaca.userservice.object.mapper.RoleMapper;
import vn.alpaca.userservice.object.request.RoleForm;
import vn.alpaca.userservice.object.dto.RoleDTO;
import vn.alpaca.userservice.service.RoleService;

import java.util.Optional;
import static vn.alpaca.util.ExtractParam.getPageable;
import static vn.alpaca.util.ExtractParam.getSort;


@RestController
@RequestMapping(value = "/roles")
public class RoleController {

    private final RoleService roleService;
    private final RoleMapper roleMapper;

    public RoleController(RoleService roleService,
                          RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

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

    @GetMapping("/{id}")
    public SuccessResponse<RoleDTO> getRole(@PathVariable("id") int id) {
        Role role = roleService.findRoleById(id);
        RoleDTO dto = roleMapper.convertToDTO(role);
        return new SuccessResponse<>(dto);
    }

    @PostMapping
    public SuccessResponse<RoleDTO> createRole(@RequestBody RoleForm form) {
        Role role = roleService.createNewRole(form);
        RoleDTO dto = roleMapper.convertToDTO(role);
        return new SuccessResponse<>(dto);
    }

    @PutMapping(value = "/{id}")
    public SuccessResponse<RoleDTO> updateRole(@PathVariable("id") int id,
                                               @RequestBody RoleForm form) {
        Role role = roleService.updateRole(id, form);
        RoleDTO dto = roleMapper.convertToDTO(role);
        return new SuccessResponse<>(dto);
    }

    @DeleteMapping("/{id}")
    public SuccessResponse<Void> deleteRole(@PathVariable("id") int id) {
        roleService.deleteRole(id);
        return new SuccessResponse<>(null);
    }
}