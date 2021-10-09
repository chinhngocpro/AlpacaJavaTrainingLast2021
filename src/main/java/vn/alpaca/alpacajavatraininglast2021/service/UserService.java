package vn.alpaca.alpacajavatraininglast2021.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;
import vn.alpaca.alpacajavatraininglast2021.object.exception.ResourceNotFoundException;
import vn.alpaca.alpacajavatraininglast2021.object.request.user.UserFilter;
import vn.alpaca.alpacajavatraininglast2021.repository.UserRepository;

import javax.persistence.EntityExistsException;

import static vn.alpaca.alpacajavatraininglast2021.specification.UserSpecification.getUserSpecification;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository,
                       PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return findUserByUsername(username);
    }

    public Page<User> findAllUsers(
            UserFilter filter,
            Pageable pageable
    ) {
        return repository.findAll(
                getUserSpecification(filter),
                pageable
        );
    }

    public User findUserById(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User id not found"
                ));
    }

    public User findUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Username not found"
                ));
    }

    public User saveUser(User user) {
        boolean usernameExist = repository.existsByUsername(user.getUsername());
        boolean emailExist = repository.existsByEmail(user.getEmail());

        if (usernameExist){
            throw new EntityExistsException("Username already exists");
        } else if (emailExist) {
            throw new EntityExistsException("Email already exists");
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return repository.save(user);
        }
    }

    public void activateUser(int userId) {
        User user = findUserById(userId);
        user.setActive(true);
        repository.save(user);
    }

    public void deactivateUser(int userId) {
        User user = findUserById(userId);
        user.setActive(false);
        repository.save(user);
    }

}
