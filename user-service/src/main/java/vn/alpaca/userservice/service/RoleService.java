package vn.alpaca.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.response.exception.ResourceNotFoundException;
import vn.alpaca.userservice.object.entity.Role;
import vn.alpaca.userservice.object.request.RoleForm;
import vn.alpaca.userservice.repository.AuthorityRepository;
import vn.alpaca.userservice.repository.RoleRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;

    public Page<Role> findAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public Role findRoleById(int id) {
        Optional<Role> role = roleRepository.findById(id);

        return role.orElseThrow(() -> new ResourceNotFoundException(
                "There's no role match with id: " + id
        ));
    }

    public Role createNewRole(RoleForm form) {
        Role role = new Role();

//        role.setName(form.getName());
//
//        if (form.getAuthorityIds() != null) {
//            form.getAuthorityIds().forEach(
//                    authorityId -> authorityRepository
//                            .findById(authorityId)
//                            .ifPresent(role::addAuthority)
//            );
//        }

        return roleRepository.save(role);
    }

    public Role updateRole(int id, RoleForm form) {
        Role role = findRoleById(id);

//        if (form.getName() != null) {
//            role.setName(form.getName());
//        }
//
//        if (form.getAuthorityIds() != null) {
//            role.getAuthorities().clear();
//            form.getAuthorityIds().forEach(
//                    authorityId -> authorityRepository
//                            .findById(authorityId)
//                            .ifPresent(role::addAuthority)
//            );
//        }

        return roleRepository.save(role);
    }

    public void deleteRole(int id) {
        roleRepository.deleteById(id);
    }
}
