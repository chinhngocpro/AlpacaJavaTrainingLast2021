package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.entity.Role;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;
import vn.alpaca.alpacajavatraininglast2021.repository.RoleRepository;
import vn.alpaca.alpacajavatraininglast2021.repository.UserRepository;

import java.util.Collection;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return findUserByUsername(username);
    }

    public Collection<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Collection<User> findActiveUsers() {
        return userRepository.findAllByActiveIsTrue();
    }

    public Page<User> findActiveUsers(Pageable pageable) {
        return userRepository.findAllByActiveIsTrue(pageable);
    }

    public Collection<User> findUsersByNameContains(String fullName) {
        return userRepository.findAllByFullNameContains(fullName);
    }

    public Page<User>
    findUsersByNameContains(String fullName, Pageable pageable) {
        return userRepository.findAllByFullNameContains(fullName, pageable);
    }

    public Collection<User> findUsersByGender(boolean gender) {
        return userRepository.findAllByGender(gender);
    }

    public Page<User> findUsersByGender(boolean gender, Pageable pageable) {
        return userRepository.findAllByGender(gender, pageable);
    }

    public Collection<User> findUserByRoleName(String roleName) {
        return userRepository.findAllByRoleName(roleName);
    }

    public Page<User> findUserByRoleName(String roleName, Pageable pageable) {
        return userRepository.findAllByRoleName(roleName, pageable);
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

    public User findUserByIdCard(String idCardNumber) {
        return userRepository.findByIdCardNumber(idCardNumber)
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

}
