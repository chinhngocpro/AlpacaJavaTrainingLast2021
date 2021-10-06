package vn.alpaca.alpacajavatraininglast2021.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.alpaca.alpacajavatraininglast2021.object.entity.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository
        extends
        JpaRepository<User, Integer>,
        JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    Page<User> findAllByActiveIsTrue(Pageable pageable);

    Page<User> findAllByFullNameContains(String fullName, Pageable pageable);

    Page<User> findAllByGender(boolean gender, Pageable pageable);

    Page<User> findAllByRoleName(String roleName, Pageable pageable);

    Optional<User> findByIdCardNumber(String idCardNumber);
}
