package vn.alpaca.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.alpaca.dto.request.UserFilter;
import vn.alpaca.dto.request.UserReq;
import vn.alpaca.dto.response.UserRes;
import vn.alpaca.exception.ResourceNotFoundException;
import vn.alpaca.userservice.object.entity.Role;
import vn.alpaca.userservice.object.entity.User;
import vn.alpaca.userservice.object.entity.User_;
import vn.alpaca.userservice.object.mapper.UserMapper;
import vn.alpaca.userservice.repository.RoleRepository;
import vn.alpaca.userservice.repository.UserRepository;
import vn.alpaca.util.NullAware;

import javax.persistence.EntityExistsException;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    private final RoleRepository roleRepo;

    private User preHandleGetObject(int userId) {
        return repository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User id not found"
                ));
    }

    private void preHandleSaveObject(User user) {
        boolean usernameExist =
                repository.existsByUsername(user.getUsername());
        boolean emailExist = repository.existsByEmail(user.getEmail());

        if (usernameExist) {
            throw new EntityExistsException("Username already exists");
        } else if (emailExist) {
            throw new EntityExistsException("Email already exists");
        }
    }

    public Page<UserRes> findAllUsers(UserFilter filter, Pageable pageable) {
        Specification<User> spec =
                UserSpecification.getUserSpecification(filter);

        return repository.findAll(spec, pageable)
                .map(mapper::convertToResModel);
    }

    public UserRes findUserById(int id) {
        return mapper.convertToResModel(
                preHandleGetObject(id)
        );
    }

    public User findUserByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Username not found"
                ));
    }

    public UserRes createUser(UserReq req) {
        User user = mapper.convertToEntity(req);
        preHandleSaveObject(user);

        if (req.getRoleId() != null) {
            Role role = roleRepo.findById(req.getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Role not found"
                    ));
            user.setRole(role);
        }


        return mapper.convertToResModel(repository.save(user));
    }

    public UserRes updateUser(int userId, UserReq req) {
        User user = preHandleGetObject(userId);
        preHandleSaveObject(user);

        try {
            NullAware.getInstance().copyProperties(user, req);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }

        if (req.getRoleId() != null) {
            Role role = roleRepo.findById(req.getRoleId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Role not found"
                    ));
            user.setRole(role);
        }

        return mapper.convertToResModel(repository.save(user));
    }

    public void activateUser(int userId) {
        User user = preHandleGetObject(userId);
        user.setActive(true);
        repository.save(user);
    }

    public void deactivateUser(int userId) {
        User user = preHandleGetObject(userId);
        user.setActive(false);
        repository.save(user);
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
