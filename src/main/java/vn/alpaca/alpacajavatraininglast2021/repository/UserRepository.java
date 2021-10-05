package vn.alpaca.alpacajavatraininglast2021.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Collection<User> findAllByActiveIsTrue();

    Page<User> findAllByActiveIsTrue(Pageable pageable);

    Collection<User> findAllByFullNameContains(String fullName);

    Page<User> findAllByFullNameContains(String fullName, Pageable pageable);

    Collection<User> findAllByGender(boolean gender);

    Page<User> findAllByGender(boolean gender, Pageable pageable);

    Collection<User> findAllByRoleName(String roleName);

    Page<User> findAllByRoleName(String roleName, Pageable pageable);

    Optional<User> findByIdCardNumber(String idCardNumber);
}
