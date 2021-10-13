package vn.alpaca.userservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.userservice.object.entity.User;
import vn.alpaca.userservice.object.entity.User_;
import vn.alpaca.response.exception.ResourceNotFoundException;
import vn.alpaca.userservice.object.request.UserFilter;
import vn.alpaca.userservice.repository.UserRepository;

import javax.persistence.EntityExistsException;

import java.util.Date;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> findAllUsers(UserFilter filter, Pageable pageable) {
        Specification<User> spec =
                UserSpecification.getUserSpecification(filter);

        return userRepository.findAll(spec, pageable);
    }

    public User findUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User id not found"
                ));
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Username not found"
                ));
    }

    public User saveUser(User user) {
        boolean usernameExist = userRepository.existsByUsername(user.getUsername());
        boolean emailExist = userRepository.existsByEmail(user.getEmail());

        if (usernameExist){
            throw new EntityExistsException("Username already exists");
        } else if (emailExist) {
            throw new EntityExistsException("Email already exists");
        } else {
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepository.save(user);
        }
    }

    public void activateUser(int userId) {
        User user = findUserById(userId);
        user.setActive(true);
        userRepository.save(user);
    }

    public void deactivateUser(int userId) {
        User user = findUserById(userId);
        user.setActive(false);
        userRepository.save(user);
    }
}

final class UserSpecification {
    public static Specification<User> getUserSpecification(UserFilter filter) {
        return Specification
                .where(hasUsername(filter.getUsername()))
                .and(hasNameContaining(filter.getFullName()))
                .and(isMale(filter.getGender()))
                .and(hasIdCardNumber(filter.getIdCardNumber()))
                .and(hasEmailContaining(filter.getEmail()))
                .and(hasDobBetween(filter.getFrom(), filter.getTo()))
                .and(hasAddressContaining(filter.getAddress()))
                .and(isActive(filter.getActive()));
    }

    private static Specification<User> hasUsername(String username) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(username) ?
                        builder.conjunction() :
                        builder.equal(
                                root.get(User_.USERNAME),
                                username
                        );
    }

    private static Specification<User> hasNameContaining(String fullName) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(fullName) ?
                        builder.conjunction() :
                        builder.like(
                                root.get(User_.FULL_NAME),
                                "%" + fullName + "%"
                        );
    }

    private static Specification<User> isMale(Boolean isMale) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(isMale) ?
                        builder.conjunction() :
                        builder.equal(root.get(User_.GENDER), isMale);
    }

    private static Specification<User> hasIdCardNumber(String idCardNumber) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(idCardNumber) ?
                        builder.conjunction() :
                        builder.equal(
                                root.get(User_.ID_CARD_NUMBER),
                                idCardNumber
                        );
    }

    private static Specification<User> hasEmailContaining(String email) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(email) ?
                        builder.conjunction() :
                        builder.like(root.get(User_.EMAIL), email);
    }

    private static Specification<User> hasDobBetween(Date from, Date to) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(from) && ObjectUtils.isEmpty(to) ?
                        builder.conjunction() :
                        ObjectUtils.isEmpty(from) ?
                                builder.lessThanOrEqualTo(
                                        root.get(User_.DATE_OF_BIRTH), to) :
                                ObjectUtils.isEmpty(to) ?
                                        builder.greaterThanOrEqualTo(
                                                root.get(User_.DATE_OF_BIRTH),
                                                from) :
                                        builder.between(
                                                root.get(User_.DATE_OF_BIRTH),
                                                from, to);
    }

    private static Specification<User> hasAddressContaining(String address) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(address) ?
                        builder.conjunction() :
                        builder.like(root.get(User_.ADDRESS), address);
    }

    private static Specification<User> isActive(Boolean active) {
        return (root, query, builder) ->
                ObjectUtils.isEmpty(active) ?
                        builder.conjunction() :
                        builder.equal(root.get(User_.ACTIVE), active);
    }
}
