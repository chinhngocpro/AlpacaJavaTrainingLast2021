package vn.alpaca.alpacajavatraininglast2021.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.exceptions.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.objects.entities.Role;
import vn.alpaca.alpacajavatraininglast2021.objects.entities.User;
import vn.alpaca.alpacajavatraininglast2021.repositories.RoleRepository;
import vn.alpaca.alpacajavatraininglast2021.repositories.UserRepository;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = findUserByUsername(username);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(
                        new SimpleGrantedAuthority(user.getRole().getName())
                )
        );
    }

    @Override
    public Collection<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public Collection<User> findActiveUsers() {
        return userRepository.findAllByActiveIsTrue();
    }

    @Override
    public Page<User> findActiveUsers(Pageable pageable) {
        return userRepository.findAllByActiveIsTrue(pageable);
    }

    @Override
    public Collection<User> findUsersByNameContains(String fullName) {
        return userRepository.findAllByFullNameContains(fullName);
    }

    @Override
    public Page<User>
    findUsersByNameContains(String fullName, Pageable pageable) {
        return userRepository.findAllByFullNameContains(fullName, pageable);
    }

    @Override
    public Collection<User> findUsersByGender(boolean gender) {
        return userRepository.findAllByGender(gender);
    }

    @Override
    public Page<User> findUsersByGender(boolean gender, Pageable pageable) {
        return userRepository.findAllByGender(gender, pageable);
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    @Override
    public User findUserByIdCard(String idCardNumber) {
        return userRepository.findByIdCardNumber(idCardNumber)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void activateUser(int userId) {
        User user = findUserById(userId);
        user.setActive(true);
        saveUser(user);
    }

    @Override
    public void deactivateUser(int userId) {
        User user = findUserById(userId);
        user.setActive(false);
        saveUser(user);
    }

    @Override
    public Collection<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRoleById(int id) {
        return roleRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(int roleId) {
        roleRepository.deleteById(roleId);
    }

    @Override
    public void deleteRole(Role role) {
        roleRepository.delete(role);
    }


}
