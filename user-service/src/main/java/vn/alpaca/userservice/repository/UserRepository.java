package vn.alpaca.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.alpaca.userservice.object.entity.User;

import java.util.Optional;

public interface UserRepository extends
        JpaRepository<User, Integer>,
        JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
