package vn.alpaca.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.alpaca.userservice.object.entity.User;

public interface UserRepository extends
        JpaRepository<User, Integer>,
        JpaSpecificationExecutor<User> {
}
