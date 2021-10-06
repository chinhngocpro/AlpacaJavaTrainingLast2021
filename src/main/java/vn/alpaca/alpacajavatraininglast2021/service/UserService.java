package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Authority;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Role;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;
import vn.alpaca.alpacajavatraininglast2021.repository.AuthorityRepository;
import vn.alpaca.alpacajavatraininglast2021.repository.RoleRepository;
import vn.alpaca.alpacajavatraininglast2021.repository.UserRepository;
import vn.alpaca.alpacajavatraininglast2021.specification.UserSpecification;

import java.util.Collection;
import java.util.Date;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthorityRepository authorityRepository;
    private final UserSpecification userSpec;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       AuthorityRepository authorityRepository,
                       UserSpecification userSpec,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authorityRepository = authorityRepository;
        this.userSpec = userSpec;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return findUserByUsername(username);
    }

    public Page<User> findAllUsers(
            String username,
            String fullName,
            Boolean isMale,
            String idCardNumber,
            String email,
            Date from,
            Date to,
            String address,
            Boolean active,
            String roleName,
            Pageable pageable
    ) {
        Specification<User> conditions = Specification
                .where(userSpec.hasUsername(username))
                .and(userSpec.hasNameContaining(fullName))
                .and(userSpec.isMale(isMale))
                .and(userSpec.hasIdCardNumber(idCardNumber))
                .and(userSpec.hasEmailContaining(email))
                .and(userSpec.hasDobBetween(from, to))
                .and(userSpec.hasAddressContaining(address))
                .and(userSpec.isActive(active))
                .and(userSpec.hasRoleName(roleName));

        return userRepository.findAll(conditions, pageable);
    }


    public User findUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void activateUser(int userId) {
        User user = findUserById(userId);
        user.setActive(true);
        saveUser(user);
    }

    public void deactivateUser(int userId) {
        User user = findUserById(userId);
        user.setActive(false);
        saveUser(user);
    }

    public Collection<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    public Role findRoleById(int id) {
        return roleRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    public void deleteRole(int roleId) {
        roleRepository.deleteById(roleId);
    }

    public void deleteRole(Role role) {
        roleRepository.delete(role);
    }

    public Page<Authority> findAllAuthorities(Pageable pageable) {
        return authorityRepository.findAll(pageable);
    }

    public Authority findAuthorityById(int id) {
        return authorityRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: Implement exception message
    }

    public Authority saveAuthority(Authority authority) {
        return authorityRepository.save(authority);
    }

    public void deleteAuthority(int id) {
        authorityRepository.deleteById(id);
    }

    public void deleteAuthority(Authority authority) {
        authorityRepository.delete(authority);
    }

}
