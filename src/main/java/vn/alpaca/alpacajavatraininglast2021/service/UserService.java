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
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;
import vn.alpaca.alpacajavatraininglast2021.repository.UserRepository;
import vn.alpaca.alpacajavatraininglast2021.specification.UserSpecification;

import java.util.Date;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final UserSpecification spec;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository,
                       UserSpecification spec,
                       PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.spec = spec;
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
                .where(spec.hasUsername(username))
                .and(spec.hasNameContaining(fullName))
                .and(spec.isMale(isMale))
                .and(spec.hasIdCardNumber(idCardNumber))
                .and(spec.hasEmailContaining(email))
                .and(spec.hasDobBetween(from, to))
                .and(spec.hasAddressContaining(address))
                .and(spec.isActive(active))
                .and(spec.hasRoleName(roleName));

        return repository.findAll(conditions, pageable);
    }


    public User findUserById(int id) {
        return repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public User findUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(ResourceNotFoundException::new);
        // TODO: implement exception message
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
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

}
