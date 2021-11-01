package vn.alpaca.userservice.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import vn.alpaca.userservice.entity.jpa.User;

import java.util.Optional;

public interface UserJpaRepository
    extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

  @Query(
      "SELECT u FROM User u "
          + "LEFT JOIN FETCH u.role AS r "
          + "LEFT JOIN FETCH r.authorities "
          + "WHERE u.username = ?1")
  Optional<vn.alpaca.userservice.entity.jpa.User> findByUsername(String username);
}
