package vn.alpaca.authserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.alpaca.authserver.entity.User;

import java.util.Optional;

public interface AuthUserRepository
        extends JpaRepository<User, Integer> {

    @Query("SELECT DISTINCT u FROM User u " +
            "LEFT JOIN FETCH u.role AS r " +
            "LEFT JOIN FETCH r.authorities AS a " +
            "WHERE u.id = ?1")
    Optional<User> findAuthUserById(int id);

    @Query("SELECT DISTINCT u FROM User u " +
            "LEFT JOIN FETCH u.role AS r " +
            "LEFT JOIN FETCH r.authorities AS a " +
            "WHERE u.username = ?1")
    Optional<User> findAuthUserByUsername(String username);
}
