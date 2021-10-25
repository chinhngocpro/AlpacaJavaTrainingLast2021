package vn.alpaca.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.alpaca.dto.request.RoleReq;
import vn.alpaca.dto.response.RoleRes;
import vn.alpaca.exception.ResourceNotFoundException;
import vn.alpaca.userservice.object.entity.Role;
import vn.alpaca.userservice.object.mapper.RoleMapper;
import vn.alpaca.userservice.repository.AuthorityRepository;
import vn.alpaca.userservice.repository.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final RoleMapper roleMapper;

    public List<RoleRes> findAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::convertToResModel)
                .collect(Collectors.toList());
    }

    public RoleRes findRoleById(int id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "There's no role match with id: " + id
                ));

        return roleMapper.convertToResModel(role);
    }

    public RoleRes createNewRole(RoleReq req) {
        Role role = new Role();

        role.setName(req.getName());

        if (req.getAuthorityIds() != null) {
            req.getAuthorityIds().forEach(
                    authorityId -> authorityRepository
                            .findById(authorityId)
                            .ifPresent(role::addAuthority)
            );
        }

        return roleMapper.convertToResModel(roleRepository.save(role));
    }

    public RoleRes updateRole(int id, RoleReq req) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "There's no role match with id: " + id
                ));

        if (req.getName() != null) {
            role.setName(req.getName());
        }

        if (req.getAuthorityIds() != null) {
            role.getAuthorities().clear();
            req.getAuthorityIds().forEach(
                    authorityId -> authorityRepository
                            .findById(authorityId)
                            .ifPresent(role::addAuthority)
            );
        }

        return roleMapper.convertToResModel(roleRepository.save(role));
    }

    public void deleteRole(int id) {
        roleRepository.deleteById(id);
    }
}
