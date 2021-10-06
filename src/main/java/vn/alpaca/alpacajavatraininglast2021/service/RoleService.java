package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Authority;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Role;
import vn.alpaca.alpacajavatraininglast2021.repository.AuthorityRepository;
import vn.alpaca.alpacajavatraininglast2021.repository.RoleRepository;
import vn.alpaca.alpacajavatraininglast2021.wrapper.request.role.RoleForm;

import java.util.Collection;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthorityService authorityService;

    @Autowired
    RoleService self;

    public Collection<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public Page<Role> findAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    public Role findRoleById(int id) {
        Optional<Role> role = roleRepository.findById(id);

        return role.orElseThrow(() -> new ResourceNotFoundException());
    }

    public Role createNewRole(RoleForm form) {
        Role role = new Role();

        role.setName(form.getName());

        if (form.getPermissions() != null) {
            form.getPermissions().forEach(authorityId -> {
                try {
                    Authority authority = authorityService.findById(authorityId);
                    role.addAuthority(authority);
                } catch (Exception e) {
                }
            });
        }

        return roleRepository.save(role);
    }

    public Role updateRole(int id, RoleForm form) {
        Role role = self.findRoleById(id);

        if (form.getName() != null) {
            role.setName(form.getName());
        }

        if (form.getPermissions() != null) {
            role.getAuthorities().clear();
            form.getPermissions().forEach(authorityId -> {
                try {
                    Authority authority = authorityService.findById(authorityId);
                    role.addAuthority(authority);
                } catch (Exception e) {
                }
            });
        }

        return roleRepository.save(role);
    }

    public void deleteRole(int id) {
        roleRepository.deleteById(id);
    }
}
