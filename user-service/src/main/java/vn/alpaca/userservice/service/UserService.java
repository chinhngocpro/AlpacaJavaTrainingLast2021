package vn.alpaca.userservice.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.alpaca.userservice.object.entity.User;
import vn.alpaca.userservice.object.wrapper.request.UserFilter;
import vn.alpaca.userservice.repository.UserRepository;

import static vn.alpaca.userservice.spec.UserSpec.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> findAllUsers(UserFilter filter, Pageable pageable) {
        Specification<User> spec = getUserSpec(filter);

        return userRepository.findAll(spec, pageable);
    }
}
