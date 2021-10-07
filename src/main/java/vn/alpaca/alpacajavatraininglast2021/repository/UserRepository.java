package vn.alpaca.alpacajavatraininglast2021.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;

import java.util.Optional;

public interface UserRepository extends
        JpaRepository<User, Integer>,
        JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);
}
